package com.feng.server.controller;

import com.feng.server.service.SettingService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author f
 * @date 2022/7/24 15:27
 */
@RestController
@RequestMapping("/users")
public class SettingController {

    @Autowired
    private SettingService settingService;

    /**
     * 读取用户的通用配置
     * @return 配置
     */
    @GetMapping("/settings")
    public ResponseEntity querySettings() {
        return settingService.querySettings();
    }

    /**
     * 通知设置更新
     * @param map
     * @return
     */
    @PostMapping("/notifications/setting")
    public ResponseEntity updateNotification(@RequestBody Map map) {
        boolean like = (Boolean)map.get("likeNotification");
        boolean pinlun = (Boolean)map.get("pinglunNotification");
        boolean gonggao = (Boolean)map.get("gonggaoNotification");
        return settingService.updateNotification(like, pinlun, gonggao);
    }

    /**
     * 分页查询黑名单列表
     * @param page 第几页
     * @param pageSize 页数
     * @return 列表
     */
    @GetMapping("/blacklist")
    public ResponseEntity findBlackList(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        return settingService.findBlackList(page, pageSize);
    }

    /**
     * 移除黑名单
     * @param deleteUserId 黑名单
     * @return entity
     */
    @DeleteMapping("/blacklist/{uid}")
    public ResponseEntity delBlackList(@PathVariable("uid") long deleteUserId) {
        return settingService.delBlackList(deleteUserId);
    }

    /**
     * 设置陌生人问题
     *  请求连接
     *      POST  /questions
     *      参数： body ：content
     */
    @PostMapping("/questions")
    public ResponseEntity saveQuestions(@RequestBody Map map) {
        //1、获取输入内容
        String content = (String) map.get("content");
        //2、调用service保存或者更新
        return settingService.saveQuestions(content);
    }

    /**
     * 修改手机号码：1.发送验证码
     */
    @PostMapping("/phone/sendVerificationCode")
    public ResponseEntity sendValidateCode(){
        settingService.sendValidateCode();
        return ResponseEntity.ok(null);
    }

    /**
     * 修改手机号 - 2 校验验证码
     */
    @PostMapping("/phone/checkVerificationCode")
    public ResponseEntity checkValidateCode(@RequestBody Map<String,String> param){
        boolean flag = settingService.checkValidateCode(param.get("verificationCode"));
        Map<String,Boolean> result = new HashMap<String,Boolean>();
        result.put("verification",flag);
        return ResponseEntity.ok(result);
    }

    /**
     * 修改手机号 - 3 保存
     */
    @PostMapping("/phone")
    public ResponseEntity changeMobile(@RequestBody Map<String,String> param, @RequestHeader("Authorization") String token){
        settingService.changeMobile(param.get("phone"),token);
        return ResponseEntity.ok(null);
    }
}
