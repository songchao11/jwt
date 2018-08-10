package com.song.enums;

/**
 * Created by song on 2018/8/10.
 */
public enum ResultStatusCode {
    SUCCESS(Integer.valueOf(1), "请求成功"),
    FAIL(Integer.valueOf(-1), "请求失败"),
    COMMON_ERROR(Integer.valueOf(-101), "系统繁忙，请稍后再试"),
    PARAMETER_ERROR(Integer.valueOf(-102), "参数错误"),
    AUTHORIZATION_ERROR(Integer.valueOf(-103), "权限错误"),
    FORM_ARGUMENTS_ERROR(Integer.valueOf(500), "Form表单参数错误"),
    BUSINESS_ERROR(Integer.valueOf(501), "业务参数不合法"),
    APPLICATION_ERROR(Integer.valueOf(502), "系统异常"),
    NOT_LOGIN_ERROR(Integer.valueOf(401), "用户未登录"),
    ILLEGAL_OPERATION_ERROR(Integer.valueOf(504), "非法操作");

    private Integer code;
    private String message;

    ResultStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
