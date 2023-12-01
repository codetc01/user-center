package com.tc.handler;

import com.tc.Exception.BaseException;
import com.tc.constant.MessageConstant;
import com.tc.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String msg = ex.getMessage();
        if(msg.contains("Duplicate entry")){
            String[] s = msg.split(" ");
            return Result.error(s[2] + MessageConstant.ALREADY_EXISTS);
        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
