package com.smartchoice.tikisupplier.server.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TikiProductResponse {

    private List<TikiProductResponseData> data = new ArrayList<>();

    private TikiProductReponsePaging paging;

    public TikiProductResponse() {
    }

    public List<TikiProductResponseData> getData() {
        return data;
    }

    public void setData(List<TikiProductResponseData> data) {
        this.data = data;
    }

    public TikiProductReponsePaging getPaging() {
        return paging;
    }

    public void setPaging(TikiProductReponsePaging paging) {
        this.paging = paging;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("data", data)
                .append("paging", paging)
                .toString();
    }
}
