package com.feng.domain.db;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  抽取 BasePojo
 * @author f
 * @date 2022/7/23 21:42
 */
@Data
public abstract class BasePojo implements Serializable {

    @TableField(fill = FieldFill.INSERT)
    private Date created;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updated;
}
