package com.feng.server.exception;

import com.feng.commons.exception.HaveFunException;
import com.feng.domain.vo.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author f
 * @date 2022/7/23 15:12
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    /**
     * 处理自定义的业务异常
     * @param ex ex
     * @return entity
     */
    @ExceptionHandler(HaveFunException.class)
    public ResponseEntity handleHaveFunException(HaveFunException ex) {
        if (null != ex.getErrData()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getErrData());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResult.error("000009", ex.getMessage())
        );
    }

    public ResponseEntity handleException(Exception ex) {
        log.error("发生未知异常", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResult.error());
    }
}
