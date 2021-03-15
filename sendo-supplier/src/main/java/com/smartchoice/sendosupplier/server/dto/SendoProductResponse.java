package com.smartchoice.sendosupplier.server.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SendoProductResponse {

    private SendoProductResponseResult result;

    public SendoProductResponse() {
    }

    public SendoProductResponseResult getResult() {
        return result;
    }

    public void setResult(SendoProductResponseResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("result", result)
                .toString();
    }
}
