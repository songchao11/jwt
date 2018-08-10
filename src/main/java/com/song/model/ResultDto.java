package com.song.model;

import com.song.enums.ResultStatusCode;

/**
 * 统一请求结果
 */
public class ResultDto {

    private Integer code;
    private String message;

    public ResultDto() {
    }

    public boolean isSuccess() {
        Integer retCode = this.getCode();
        return retCode != null && retCode.equals(ResultStatusCode.SUCCESS.getCode());
    }

    public boolean isFailed() {
        return !this.isSuccess();
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "Result [code=" + this.code + ", message=" + this.message + "]";
    }

    public ResultDto makeResult(Integer retCode, String retMsg) {
        if(retCode != null) {
            this.setCode(retCode);
        }

        if(retMsg != null) {
            this.setMessage(retMsg);
        }

        return this;
    }

    public ResultDto makeResult(ResultStatusCode retCode) {
        return this.makeResult(retCode.getCode(), retCode.getMessage());
    }

    public ResultDto makeResult(ResultStatusCode retCode, String retMsg) {
        return this.makeResult(retCode.getCode(), retMsg);
    }

    public ResultDto makeResult(Integer retCode) {
        return this.makeResult((Integer)retCode, (String)null);
    }

    public ResultDto makeResult(ResultDto result) {
        return this.makeResult(result.getCode(), result.getMessage());
    }

    public ResultDto makeParameterErrorResult() {
        return this.makeResult(ResultStatusCode.BUSINESS_ERROR, ResultStatusCode.BUSINESS_ERROR.getMessage());
    }

    public ResultDto makeParameterErrorResult(String retMsg) {
        return this.makeResult(ResultStatusCode.BUSINESS_ERROR, retMsg);
    }

    public ResultDto success() {
        return this.makeResult(ResultStatusCode.SUCCESS);
    }

    public ResultDto failed() {
        return this.makeResult(ResultStatusCode.FAIL);
    }

    public ResultDto failed(String retMsg) {
        return this.makeResult(ResultStatusCode.FAIL.getCode(), retMsg);
    }

    public ResultDto unAuthorized() {
        return this.makeResult(ResultStatusCode.AUTHORIZATION_ERROR.getCode(), ResultStatusCode.AUTHORIZATION_ERROR.getMessage());
    }

    public ResultDto unAuthorized(String message) {
        return this.makeResult(ResultStatusCode.AUTHORIZATION_ERROR.getCode(), message);
    }

}
