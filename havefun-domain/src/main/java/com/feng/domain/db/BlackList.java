package com.feng.domain.db;

import lombok.Data;

/**
 * @author f
 * @date 2022/7/24 15:18
 */
@Data
public class BlackList extends BasePojo{
    private Long id;
    private Long userId;
    private Long blackUserId;
}
