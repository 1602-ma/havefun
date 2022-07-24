package com.feng.domain.db;

import lombok.Data;

/**
 * @author f
 * @date 2022/7/24 15:15
 */
@Data
public class Settings extends BasePojo{
    private Long id;
    private Long userId;
    private Boolean likeNotification;
    private boolean pinglunNotification;
    private boolean gonggaoNotification;
}
