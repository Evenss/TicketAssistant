package com.graduation.even.graduationclient.user;

import java.util.Date;

/**
 * Created by Even on 2017/11/7.
 */

public class UserInfo {
    private int userId;
    private String token;
    private String email;
    private int invalidTime;                                    //登录后有效时间
    private long loginTime;                                     //登录时间
    private boolean tokenInvalid;                               //token是否过期标志

    private static UserInfo userInfo;

    private UserInfo(){}

    public static UserInfo getInstance(){
        if (userInfo == null){
            userInfo = new UserInfo();
        }

        return userInfo;
    }
    // 注册之后调用这个
    public void setInfo(int userId, String token, int invalidTime) {
        this.userId = userId;
        this.token = token;
        this.invalidTime = invalidTime;
        this.loginTime = new Date().getTime() / 1000;
        tokenInvalid = false;
    }

    //登录之后调用这个
    public void setInfo(int userId, String token, String email, int invalidTime) {
        this.userId = userId;
        this.token = token;
        this.email = email;
        this.invalidTime = invalidTime;
        this.loginTime = new Date().getTime() / 1000;
        tokenInvalid = false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * true token 无效  false token 有效
     * @return
     */
    public boolean isTokenInvalid(){
        if (!tokenInvalid){
            tokenInvalid = (new Date().getTime() / 1000) - loginTime > invalidTime;
        }

        return tokenInvalid;
    }
}
