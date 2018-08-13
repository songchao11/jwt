package com.song.dto;

import com.song.enums.ResultStatusCode;

/**
 * 统一请求结果(带实体)
 */
public class ObjectResultDto<T> extends ResultDto {

    private T data;

    public ObjectResultDto(){

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ObjectResultDto<T> makeResult(Integer retCode, String retMsg) {
        if(retCode != null) {
            this.setCode(retCode);
        }

        if(retMsg != null) {
            this.setMessage(retMsg);
        }

        return this;
    }

    public ObjectResultDto<T> makeResult(ResultStatusCode retCode) {
        return this.makeResult(retCode.getCode(), retCode.getMessage());
    }

    public ObjectResultDto<T> makeResult(ResultStatusCode retCode, String retMsg) {
        return this.makeResult(retCode.getCode(), retMsg);
    }

    public ObjectResultDto<T> makeResult(Integer retCode) {
        return this.makeResult((Integer)retCode, (String)null);
    }

    public ObjectResultDto<T> makeResult(ResultDto result) {
        return this.makeResult(result.getCode(), result.getMessage());
    }

    public ObjectResultDto<T> makeParameterErrorResult() {
        return this.makeResult(ResultStatusCode.BUSINESS_ERROR, ResultStatusCode.BUSINESS_ERROR.getMessage());
    }

    public ObjectResultDto<T> makeParameterErrorResult(String retMsg) {
        return this.makeResult(ResultStatusCode.BUSINESS_ERROR, retMsg);
    }

    public ObjectResultDto<T> success() {
        return this.makeResult(ResultStatusCode.SUCCESS);
    }

    public ObjectResultDto<T> failed() {
        return this.makeResult(ResultStatusCode.FAIL);
    }

    public ObjectResultDto<T> failed(String retMsg) {
        return this.makeResult(ResultStatusCode.FAIL.getCode(), retMsg);
    }

    public ObjectResultDto<T> unAuthorized() {
        return this.makeResult(ResultStatusCode.AUTHORIZATION_ERROR.getCode(), ResultStatusCode.AUTHORIZATION_ERROR.getMessage());
    }

    public ObjectResultDto<T> unAuthorized(String message) {
        return this.makeResult(ResultStatusCode.AUTHORIZATION_ERROR.getCode(), message);
    }

}
