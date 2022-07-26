package com.feng.domain.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author f
 * @date 2022/7/26 21:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement extends BasePojo{
    private String id;
    private String title;
    private String description;
}
