package com.smartchoice.sendosupplier.server.dto;

public class SendoTokenResult {
    private String token;
    private String expires;

    public SendoTokenResult() {
    }

    public String getToken() {
        return token;
    }

    public String getExpires() {
        return expires;
    }
}
