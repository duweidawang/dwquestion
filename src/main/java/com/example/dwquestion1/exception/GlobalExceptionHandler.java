package com.example.dwquestion1.exception;

import com.example.dwquestion1.common.BaseResponse;
import com.example.dwquestion1.common.ErrorCode;
import com.example.dwquestion1.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }

//    @ExceptionHandler(NotRoleException.class)
//    public BaseResponse<?> notRoleExceptionHandler(RuntimeException e) {
//        log.error("NotRoleException", e);
//        return ResultUtils.error(ErrorCode.NO_AUTH_ERROR, "无权限");
//    }
//
//    @ExceptionHandler(NotLoginException.class)
//    public BaseResponse<?> notLoginExceptionHandler(RuntimeException e) {
//        log.error("NotLoginException", e);
//        return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR, "未登录");
//    }
}
