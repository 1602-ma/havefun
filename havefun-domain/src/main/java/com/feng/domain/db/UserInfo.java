package com.feng.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author f
 * @date 2022/7/23 22:43
 */
@Data
public class UserInfo extends BasePojo{
    @TableId(type = IdType.INPUT)
    private Long id;
    private String nickname;
    private String avatar;
    private String birthday;
    private String gender;
    private Integer age;
    private String city;
    private String income;
    private String education;
    private String profession;
    private Integer marriage;
    private String tags;
    private String coverPic;
}
