package com.graduation.even.graduationclient.net.bean.request;

/**
 * Created by Even on 2017/11/8.
 */

public class LoginRequest {
    private String phone;
    private String password;

    public LoginRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
