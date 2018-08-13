package com.song.dto;

import com.song.enums.ResultStatusCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一请求结果(分页)
 */
public class PagedResultDto<T> extends ListResultDto<T> {

    //页号
    private Integer pageNo;
    //页面大小
    private Integer pageSize;

    public Integer getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PagedResultDto() {
    }

    public PagedResultDto(Integer pageNo, Integer pageSize, List<T> items, Integer totalCount) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.items = items;
        this.totalCount = totalCount;
    }

    public PagedResultDto(List<T> items, int totalCount) {
        super(items, Integer.valueOf(totalCount));
    }

    public List<T> getItems() {
        if(this.items == null) {
            this.items = new ArrayList();
        }

        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public PagedResultDto<T> makeResult(Integer error, String message, List<T> list, Integer totalCount) {
        super.makeResult(error, message);
        if(list != null) {
            this.setItems(list);
            this.setTotalCount(totalCount);
        }

        return this;
    }

    public PagedResultDto<T> makeResult(Integer error, String message) {
        return this.makeResult(error, message, (List)null, (Integer)null);
    }

    public PagedResultDto<T> makeResult(ResultStatusCode error, String message) {
        return this.makeResult(error.getCode(), message);
    }

    public PagedResultDto<T> makeResult(ResultStatusCode retCode) {
        return this.makeResult(retCode.getCode(), retCode.getMessage());
    }

    public PagedResultDto<T> makeResult(Integer error) {
        return this.makeResult((Integer)error, (String)null);
    }

    public ListResultDto<T> makeResult(ResultDto result) {
        return this.makeResult(result.getCode(), result.getMessage());
    }

    public PagedResultDto<T> makeResult(PagedResultDto<T> result) {
        return this.makeResult(result.getCode(), result.getMessage(), result.getItems(), result.totalCount);
    }

    public PagedResultDto<T> makeParameterErrorResult() {
        return this.makeResult(ResultStatusCode.BUSINESS_ERROR);
    }

    public PagedResultDto<T> makeParameterErrorResult(String message) {
        return this.makeResult(ResultStatusCode.BUSINESS_ERROR, message);
    }

    public PagedResultDto<T> success() {
        return this.makeResult(ResultStatusCode.SUCCESS);
    }

    public PagedResultDto<T> failed() {
        return this.makeResult(ResultStatusCode.FAIL);
    }

    public PagedResultDto<T> failed(String message) {
        return this.makeResult(ResultStatusCode.FAIL.getCode(), message);
    }

}
