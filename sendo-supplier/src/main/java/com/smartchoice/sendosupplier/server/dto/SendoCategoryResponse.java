package com.smartchoice.sendosupplier.server.dto;

import java.util.List;

import com.smartchoice.common.dto.CategoryResponse;

public class SendoCategoryResponse {
    List<CategoryResponse> result;

    public SendoCategoryResponse() {
    }

    public SendoCategoryResponse(List<CategoryResponse> result) {
        this.result = result;
    }

    public List<CategoryResponse> getResult() {
        return result;
    }

    public void setResult(List<CategoryResponse> result) {
        this.result = result;
    }
}
