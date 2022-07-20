package com.feng.domain.db;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author f
 * @date 2022/7/20 22:08
 */
@Data
public class User implements Serializable {
    private Long id;
    private String mobile;
    private String password;
    private Date created;
    private Date updated;
}
