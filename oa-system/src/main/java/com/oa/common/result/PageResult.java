package com.oa.common.result;

import lombok.Data;

import java.util.List;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-01-07  15:34
 */
@Data
public class PageResult<T> {
    private long total;
    private List<T> rows;
    private int pageNum;
    private int pageSize;
    private int pages;

    public PageResult() {}

    public PageResult(List<T> list, long total) {
        this.rows = list;
        this.total = total;
    }

    public PageResult(List<T> list, long total, int pageNum, int pageSize) {
        this.rows = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (int) Math.ceil((double) total / pageSize);
    }
}
