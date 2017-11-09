package com.graduation.even.graduationclient.net.bean.request;

/**
 * Created by Even on 2017/11/9.
 */

public class RegisterRequest {
    private String phone;
    private String password;

    public RegisterRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
