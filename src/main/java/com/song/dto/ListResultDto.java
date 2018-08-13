package com.song.dto;

import com.song.enums.ResultStatusCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一请求结果(列表)
 */
public class ListResultDto<T> extends ResultDto {

    protected List<T> items;
    //数据总记录数
    protected Integer totalCount;

    public Integer getTotalCount() {
        return Integer.valueOf(this.totalCount == null?0:this.totalCount.intValue());
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public ListResultDto() {
    }

    public ListResultDto(List<T> items, Integer totalCount) {
        this.items = items;
        this.totalCount = totalCount;
    }

    public List<T> getItems() {
        if(this.items == null) {
            this.items = new ArrayList();
        }

        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
        if(!items.isEmpty()) {
            this.totalCount = Integer.valueOf(items.size());
        }

    }

    public ListResultDto<T> makeResult(Integer error, String message, List<T> list) {
        super.makeResult(error, message);
        if(list != null) {
            this.setItems(list);
            this.setTotalCount(Integer.valueOf(list.size()));
        }

        return this;
    }

    public ListResultDto<T> makeResult(Integer error, String message) {
        return this.makeResult(error, message, (List)null);
    }

    public ListResultDto<T> makeResult(ResultStatusCode error, String message) {
        return this.makeResult(error.getCode(), message);
    }

    public ListResultDto<T> makeResult(ResultStatusCode retCode) {
        return this.makeResult(retCode.getCode(), retCode.getMessage());
    }

    public ListResultDto<T> makeResult(Integer error) {
        return this.makeResult((Integer)error, (String)null);
    }

    public ListResultDto<T> makeResult(ResultDto result) {
        return this.makeResult(result.getCode(), result.getMessage());
    }

    public ListResultDto<T> makeResult(ListResultDto<T> result) {
        return this.makeResult(result.getCode(), result.getMessage(), result.getItems());
    }

    public ListResultDto<T> makeParameterErrorResult() {
        return this.makeResult(ResultStatusCode.BUSINESS_ERROR);
    }

    public ListResultDto<T> makeParameterErrorResult(String message) {
        return this.makeResult(ResultStatusCode.BUSINESS_ERROR, message);
    }

    public ListResultDto<T> success() {
        return this.makeResult(ResultStatusCode.SUCCESS);
    }

    public ListResultDto<T> failed() {
        return this.makeResult(ResultStatusCode.FAIL);
    }

    public ListResultDto<T> failed(String message) {
        return this.makeResult(ResultStatusCode.FAIL.getCode(), message);
    }

    public String toString() {
        return "ListResultDto{items=" + this.getItems() + "totalCount" + this.getTotalCount() + '}';
    }

}
