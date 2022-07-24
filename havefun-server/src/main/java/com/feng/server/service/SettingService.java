package com.feng.server.service;

import com.aliyuncs.utils.StringUtils;
import com.feng.commons.constant.RedisKeyConst;
import com.feng.commons.exception.HaveFunException;
import com.feng.commons.templates.SmsTemplate;
import com.feng.domain.db.Question;
import com.feng.domain.db.Settings;
import com.feng.domain.db.User;
import com.feng.domain.db.UserInfo;
import com.feng.domain.vo.ErrorResult;
import com.feng.domain.vo.PageResult;
import com.feng.domain.vo.SettingsVo;
import com.feng.dubbo.api.BlackListApi;
import com.feng.dubbo.api.QuestionApi;
import com.feng.dubbo.api.SettingsApi;
import com.feng.dubbo.api.UserApi;
import com.feng.server.interceptor.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author f
 * @date 2022/7/24 15:28
 */
@Service
@Slf4j
public class SettingService {

    @Reference
    private QuestionApi questionApi;

    @Reference
    private UserApi userApi;

    @Reference
    private SettingsApi settingsApi;

    @Reference
    private BlackListApi blackListApi;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsTemplate smsTemplate;


    /**
     * 查询配置
     * @return entity
     */
    public ResponseEntity querySettings() {
        User user = UserHolder.getUser();
        SettingsVo vo = new SettingsVo();
        //查询陌生人问题
        Question question = questionApi.findByUserId(user.getId());
        if (null != question) {
            vo.setStrangerQuestion(question.getTxt());
        }
        //查询用户的通知设置
        Settings settings = settingsApi.findByUserId(user.getId());
        if (null != settings) {
            BeanUtils.copyProperties(settings, vo);
        }
        vo.setPhone(user.getMobile());
        return ResponseEntity.ok(vo);
    }

    /**
     * 通知设置
     * @param like like
     * @param pinlun pinlun
     * @param gonggao gonggao
     * @return entity
     */
    public ResponseEntity updateNotification(boolean like, boolean pinlun, boolean gonggao) {
        Settings settings = settingsApi.findByUserId(UserHolder.getUserId());
        if (null == settings) {
            settings = new Settings();
            settings.setUserId(UserHolder.getUserId());
            settings.setLikeNotification(like);
            settings.setGonggaoNotification(gonggao);
            settings.setPinglunNotification(pinlun);
            settingsApi.save(settings);
        }else {
            settings.setLikeNotification(like);
            settings.setGonggaoNotification(gonggao);
            settings.setPinglunNotification(pinlun);
            settingsApi.update(settings);
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 分页查询当前用户的黑名单列表
     * @param page 第几页
     * @param pageSize 页数
     * @return entity
     */
    public ResponseEntity findBlackList(int page, int pageSize) {
        Long userId = UserHolder.getUserId();
        PageResult<UserInfo> pageResult = blackListApi.findBlackList(page,pageSize,userId);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 移除黑名单
     * @param deleteUserId id
     * @return entity
     */
    public ResponseEntity delBlackList(Long deleteUserId) {
        //1、获取当前用户的userid
        Long userId = UserHolder.getUserId();
        //2、调用api删除黑名单数据
        blackListApi.delete(userId,deleteUserId);
        return ResponseEntity.ok(null);
    }

    /**
     * 保存或者更新陌生问问题
     */
    public ResponseEntity saveQuestions(String content) {
        //1、根据当前登录用户查询问题
        Question question = questionApi.findByUserId(UserHolder.getUserId());
        //2、如果未设置，保存
        if(question == null) {
            question = new Question();
            question.setUserId(UserHolder.getUserId());
            question.setTxt(content);
            questionApi.save(question);
        }else {
            //3、如果以设置，更新
            question.setTxt(content);
            questionApi.update(question);
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 修改手机号码：1.发送验证码
     */
    public void sendValidateCode() {
        String mobile = UserHolder.getUser().getMobile();
        // redis中存入验证码的key
        String key = RedisKeyConst.CHANGE_MOBILE_VALIDATE_CODE + mobile;
        // redis中的验证码
        String codeInRedis = (String) redisTemplate.opsForValue().get(key);
        log.debug("========== redis中的验证码 修改手机号码:{},{}",mobile,codeInRedis);
        if(StringUtils.isNotEmpty(codeInRedis)){
            // 已经发送过了
            //return ErrorResult.duplicate();
            throw new HaveFunException(ErrorResult.duplicate());
        }
        // 生成验证码
        String validateCode = RandomStringUtils.randomNumeric(6);
        // 发送验证码
        log.debug("========== 发送验证码 修改手机号码:{},{}",mobile,validateCode);
//        Map<String, String> smsResult = smsTemplate.sendValidateCode(mobile, validateCode);
//        if(null != smsResult){
//            // 发送失败
//            throw new HaveFunException(ErrorResult.fail());
//        }
        // 存入redis，有效期5分钟
        log.info("========== 验证码存入redis");
        redisTemplate.opsForValue().set(key,validateCode, 5, TimeUnit.MINUTES);
    }

    /**
     * 修改手机号 - 2 校验验证码
     * @param verificationCode
     * @return
     */
    public boolean checkValidateCode(String verificationCode) {
        User user = UserHolder.getUser();
        String phone = user.getMobile();
        // redis中存入验证码的key
        String key = RedisKeyConst.CHANGE_MOBILE_VALIDATE_CODE + phone;
        // redis中的验证码
        String codeInRedis = redisTemplate.opsForValue().get(key).toString();
        // 防止重复提交
        redisTemplate.delete(key);
        log.debug("==========修改手机号 校验 验证码:{},{},{}",phone,codeInRedis,verificationCode);
        if(StringUtils.isEmpty(codeInRedis)){
            throw new HaveFunException(ErrorResult.loginError());
        }
        if(!codeInRedis.equals(verificationCode)){
            return false;
        }
        return true;
    }

    /**
     * 修改手机号 - 3 保存
     * @param phone
     * @param token
     */
    public void changeMobile(String phone,String token) {
        Long userId = UserHolder.getUserId();
        userApi.updateMobile(userId, phone);
        log.debug("修改手机号码成功(old:{})=>(new:{})",UserHolder.getUser().getMobile(),phone);
        // 删除token,让用户重新登陆
        String key = RedisKeyConst.TOKEN + token;
        redisTemplate.delete(key);
    }
}
