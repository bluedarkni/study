package com.nijunyang.mybatis.page;

/**
 * @author nijunyang
 * @since 2021/8/24
 */
public class PageRequest {
    public static final int DEFAULT_PAGE_SIZE = 20;
    private int page;
    private int pageSize;

    public PageRequest() {
    }

    public int getPage() {
        return this.page > 0 ? this.page : 1;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize > 0 ? this.pageSize : 20;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return (this.getPage() - 1) * this.pageSize;
    }

    public int getPageSizeByLimit(int maxPageSize) {
        if (this.pageSize > maxPageSize) {
            return maxPageSize;
        } else {
            return this.pageSize <= 0 ? 20 : this.pageSize;
        }
    }
}
