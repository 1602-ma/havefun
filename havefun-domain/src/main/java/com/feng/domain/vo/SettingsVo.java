package com.feng.domain.vo;

import lombok.Data;

/**
 * @author f
 * @date 2022/7/24 15:21
 */
@Data
public class SettingsVo {
    private Long id;
    private String strangerQuestion;
    private String phone;
    private Boolean likeNotification;
    private Boolean pinglunNotification;
    private Boolean gonggaoNotification;
}
