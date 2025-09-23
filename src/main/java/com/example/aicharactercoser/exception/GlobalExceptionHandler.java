package com.example.aicharactercoser.exception;


import com.example.aicharactercoser.common.BaseResponse;
import com.example.aicharactercoser.common.ResultUtils;

import io.swagger.v3.oas.annotations.Hidden;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//hidden防止 springboot 扫描该类
@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);

        // 对于普通请求，返回标准 JSON 响应
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);

        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");
    }

}