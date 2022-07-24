package com.feng.server.service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.utils.StringUtils;
import com.feng.commons.exception.HaveFunException;
import com.feng.commons.templates.FaceTemplate;
import com.feng.commons.templates.OssTemplate;
import com.feng.commons.templates.SmsTemplate;
import com.feng.domain.db.User;
import com.feng.domain.db.UserInfo;
import com.feng.domain.vo.ErrorResult;
import com.feng.domain.vo.UserInfoVo;
import com.feng.dubbo.api.UserApi;
import com.feng.dubbo.api.UserInfoApi;
import com.feng.server.utils.GetAgeUtil;
import com.feng.server.utils.JwtUtil;
import com.sun.xml.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author f
 * @date 2022/7/20 22:55
 */
@Service
@Slf4j
public class UserService {

    @Reference
    private UserApi userApi;

    @Value("${havefun.redisValidateCodeKeyPrefix}")
    private String redisValidateCodeKeyPrefix;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Reference
    private UserInfoApi userInfoApi;

    @Autowired
    private FaceTemplate faceTemplate;

    @Autowired
    private OssTemplate ossTemplate;

    /**
     * 根据手机号查询用户
     * @param mobile mobile
     * @return user
     */
    public ResponseEntity findByMobile(String mobile) {
        User user = userApi.findByMobile(mobile);
        return ResponseEntity.ok(user);
    }

    /**
     * 新增用户
     * @param mobile mobile
     * @param password password
     * @return null
     */
    public ResponseEntity saveUser(String mobile, String password) {
        User user = new User();
        user.setMobile(mobile);
        user.setPassword(password);
        userApi.save(user);
        return ResponseEntity.ok(null);
    }

    /**
     * 发送验证码
     * @param phone phone
     */
    public void sendValidateCode(String phone) {
        //1.redis中存入验证码的key
        String key = redisValidateCodeKeyPrefix + phone;
        //2.redis中的验证码
        String codeInRedis = redisTemplate.opsForValue().get("key");
        //3.如果存在，提示上次发送的验证码还未失效
        if (StringUtils.isNotEmpty(codeInRedis)) {
            throw new HaveFunException(ErrorResult.duplicate());
        }
        //4.生成验证码
        String validateCode = RandomStringUtils.randomNumeric(6);
        //5.发送验证码
        log.debug("发送验证码：{}，{}", phone, validateCode);
//        Map<String, String> smsRs = smsTemplate.sendValidateCode(phone, validateCode);
//        if (null != smsRs) {
//            throw new HaveFunException(ErrorResult.fail());
//        }
        //6.将验证码存入redis,有效时间5分钟
        log.info("将验证码存入redis");
        redisTemplate.opsForValue().set(key, validateCode, 5, TimeUnit.MINUTES);
    }


    /**
     * 登录或注册
     * @param phone phone
     * @param verificationCode code
     * @return map
     */
    public Map<String, Object> loginVerification(String phone, String verificationCode) {
        String key = redisValidateCodeKeyPrefix + phone;
        String codeInRedis = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        log.debug("=============== 校验 验证码：{}，{}，{}", phone, codeInRedis, verificationCode);
        if (StringUtils.isEmpty(codeInRedis)) {
            throw new HaveFunException(ErrorResult.loginError());
        }
        if (!codeInRedis.equals(verificationCode)) {
            throw new HaveFunException(ErrorResult.validateCodeError());
        }

        User user = userApi.findByMobile(phone);
        boolean isNew = false;
        if (null == user) {
            user = new User();
            user.setMobile(phone);
            user.setPassword(DigestUtils.md5Hex(phone.substring(phone.length() - 6)));
            log.info("======================= 添加新用户 {}", phone);
            Long userId = userApi.save(user);
            user.setId(userId);
            isNew = true;
        }
        String token = jwtUtil.createJwt(phone, user.getId());
        String userString = JSON.toJSONString(user);
        redisTemplate.opsForValue().set("TOKEN_" + token, userString, 1, TimeUnit.DAYS);
        log.debug("===================== 签发token：{}", token);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isNew", isNew);
        resultMap.put("token", token);
        return resultMap;
    }

    /**
     * 通过token获取登录用户信息
     * @param token token
     * @return user
     */
    public User getUserByToken(String token) {
        String key = "TOKEN_" + token;
        String userJsonStr = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(userJsonStr)) {
            return null;
        }
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
        User user = JSON.parseObject(userJsonStr, User.class);
        return user;
    }

    /**
     * 完善用户信息
     * @param userInfo userInfo
     * @param token token
     */
    public void saveUserInfo(UserInfo userInfo, String token) {
        User user = getUserByToken(token);
        if (null == user) {
            throw new HaveFunException("登录超时，请重新登录");
        }
        userInfo.setId(user.getId());
        userInfoApi.save(userInfo);
    }

    /**
     * 上传用户头像处理
     * @param headPhoto photo
     * @param token token
     */
    public void updateUserAvatar(MultipartFile headPhoto, String token) {
        User user = getUserByToken(token);
        if (null == user) {
            throw new HaveFunException("登录超时， 请重新登录");
        }
        try {
            String filename = headPhoto.getOriginalFilename();
            byte[] bytes = headPhoto.getBytes();
            if (!faceTemplate.detect(bytes)) {
                throw new HaveFunException("没有检测到人脸,请重新上传");
            }
            String avatar = ossTemplate.upload(filename, headPhoto.getInputStream());
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setAvatar(avatar);
            userInfoApi.update(userInfo);
        } catch (IOException e) {
            log.error("上传头像失败");
            throw new HaveFunException("上传头像失败，请稍后再试");
        }
    }

    /**
     * 通过id查询用户基本信息
     * @param userId userId
     * @return userInfo
     */
    public UserInfoVo findUserInfoById(Long userId) {
        UserInfo userInfo = userInfoApi.findById(userId);
        UserInfoVo vo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, vo);
        if (null != userInfo.getAge()) {
            vo.setAge(String.valueOf(userInfo.getAge().intValue()));
        }
        return vo;
    }

    /**
     * 保存用户基本信息
     * @param vo
     * @param token token
     */
    public void updateUserInfo(UserInfoVo vo, String token) {
        User user = getUserByToken(token);
        if (null == user) {
            throw new HaveFunException("登录超时，请重新登录");
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(vo, userInfo);
        int age = GetAgeUtil.getAge(userInfo.getBirthday());
        userInfo.setAge(age);
        userInfo.setId(user.getId());
        userInfoApi.update(userInfo);
    }

    /**
     * 更新用户头像
     * @param headPhoto photo
     * @param token token
     */
    public void header(MultipartFile headPhoto, String token) {
        User user = getUserByToken(token);
        if (null == user) {
            throw new HaveFunException("登录超时，请重新登录");
        }
        try {
            String filename = headPhoto.getOriginalFilename();
            byte[] bytes = headPhoto.getBytes();
//            if (!faceTemplate.detect(bytes)) {
//                throw new HaveFunException("没有检测到人脸，请重新上传");
//            }
//            String avatar = ossTemplate.upload(filename, headPhoto.getInputStream());
            UserInfo userInfo = new UserInfo();
            user.setId(user.getId());
//            userInfo.setAvatar(avatar);
            userInfoApi.update(userInfo);
        } catch (IOException e) {
            log.error("上传头像失败");
            throw new HaveFunException("更新头像失败哦，请稍后再试");
        }
    }
}
