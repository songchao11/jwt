package com.song.controller;

import com.song.dto.ResultDto;
import com.song.enums.ResultStatusCode;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理
 */
@RestControllerAdvice
public class ExceptionController {

    /*
     * 捕获shiro的异常
     */
    @ExceptionHandler(ShiroException.class)
    public ResultDto handle401(){
        ResultDto resultDto = new ResultDto();
        return resultDto.makeResult(ResultStatusCode.NOT_LOGIN_ERROR);
    }

    /*
     * 捕获其他异常
     */
    @ExceptionHandler(Exception.class)
    public ResultDto globalException(HttpServletRequest request, Throwable ex){
        ResultDto resultDto = new ResultDto();
        return resultDto.makeResult(getStatus(request).value(), ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status.status_code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
