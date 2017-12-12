package com.graduation.even.graduationclient.net.bean.request;

/**
 * Created by Even on 2017/11/9.
 */

public class ChangePwdRequest {
    private String token;
    private int userId;
    private String passwordOld;
    private String passwordNew;

    public ChangePwdRequest(String token, int userId, String pwdOld, String pwdNew) {
        this.token = token;
        this.userId = userId;
        this.passwordOld = pwdOld;
        this.passwordNew = pwdNew;
    }
}
