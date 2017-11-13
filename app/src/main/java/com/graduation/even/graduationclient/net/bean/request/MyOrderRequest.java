package com.graduation.even.graduationclient.net.bean.request;

/**
 * Created by Even on 2017/11/13.
 */

public class MyOrderRequest {
    private String token;
    private int userId;
    private int pageSize;
    private int pageNumber;

    public MyOrderRequest(String token, int userId, int pageSize, int pageNumber) {
        this.token = token;
        this.userId = userId;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }
}
