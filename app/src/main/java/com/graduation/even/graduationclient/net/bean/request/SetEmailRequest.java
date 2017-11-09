package com.graduation.even.graduationclient.net.bean.request;

/**
 * Created by Even on 2017/11/9.
 */

public class SetEmailRequest {
    private String token;
    private int userId;
    private String email;

    public SetEmailRequest(String token, int userId, String email) {
        this.token = token;
        this.userId = userId;
        this.email = email;
    }
}
