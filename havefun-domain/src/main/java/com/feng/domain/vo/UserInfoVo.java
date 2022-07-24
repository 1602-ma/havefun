package com.feng.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author f
 * @date 2022/7/23 22:46
 */
@Data
public class UserInfoVo implements Serializable {
    private Long id;
    private String nickname;
    private String avatar;
    private String birthday;
    private String gender;
    private String age;
    private String city;
    private String income;
    private String education;
    private String profession;
    private Integer marriage;
}
