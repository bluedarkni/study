package com.nijunyang.mybatis.page;

import java.util.List;

/**
 * @author nijunyang
 * @since 2021/8/24
 */
public class Pagination<T>{
    private List<T> result;
    private long total;
    private long page;
    private long pageSize;

    public Pagination() {
    }

    public long getTotalPage() {
        return this.total > 0L && this.pageSize > 0L ? (this.total + this.pageSize - 1L) / this.pageSize : 0L;
    }

    public List<T> getResult() {
        return this.result;
    }

    public long getTotal() {
        return this.total;
    }

    public long getPage() {
        return this.page;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
