package com.smartchoice.sendosupplier.server.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SendoProductResponseResult {

    private List<SendoProductResponseData> data = new ArrayList<>();

    private Long total_records;

    public SendoProductResponseResult() {
    }

    public List<SendoProductResponseData> getData() {
        return data;
    }

    public void setData(List<SendoProductResponseData> data) {
        this.data = data;
    }

    public Long getTotal_records() {
        return total_records;
    }

    public void setTotal_records(Long total_records) {
        this.total_records = total_records;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("data", data)
                .append("total_records", total_records)
                .toString();
    }
}
