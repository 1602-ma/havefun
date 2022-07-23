package com.feng.commons.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 * @author f
 * @date 2022/7/23 15:10
 */
@Data
@NoArgsConstructor
public class HaveFunException extends RuntimeException{

    private Object errData;

    public HaveFunException(String errMessage) {
        super(errMessage);
    }

    public HaveFunException(Object data) {
        super();
        this.errData = data;
    }
}
