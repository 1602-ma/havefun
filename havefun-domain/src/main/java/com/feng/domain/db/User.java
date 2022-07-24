package com.feng.domain.db;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author f
 * @date 2022/7/20 22:08
 */
@Data
public class User extends BasePojo {
    private Long id;
    private String mobile;
    @JSONField(serialize = false)
    private String password;
}
