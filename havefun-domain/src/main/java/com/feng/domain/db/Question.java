package com.feng.domain.db;

import lombok.Data;

/**
 * @author f
 * @date 2022/7/24 15:17
 */
@Data
public class Question extends BasePojo{
    private Long id;
    private Long userId;
    private String txt;
}
