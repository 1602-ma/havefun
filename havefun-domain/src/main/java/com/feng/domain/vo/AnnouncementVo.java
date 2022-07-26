package com.feng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author f
 * @date 2022/7/26 21:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementVo {
    private String id;
    private String title;
    private String description;
    private String createDate;
}
