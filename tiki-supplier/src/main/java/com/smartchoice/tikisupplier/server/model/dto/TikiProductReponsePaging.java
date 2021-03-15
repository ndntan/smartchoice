package com.smartchoice.tikisupplier.server.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TikiProductReponsePaging {

    private Long total;
    private Long current_page;
    private Long last_page;

    public TikiProductReponsePaging() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Long current_page) {
        this.current_page = current_page;
    }

    public Long getLast_page() {
        return last_page;
    }

    public void setLast_page(Long last_page) {
        this.last_page = last_page;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("total", total)
                .append("current_page", current_page)
                .append("last_page", last_page)
                .toString();
    }
}
