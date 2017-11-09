package com.graduation.even.graduationclient.net.bean.request;

/**
 * Created by Even on 2017/11/9.
 */

public class LogoutRequest {
    private String token;

    public LogoutRequest(String token) {
        this.token = token;
    }
}
