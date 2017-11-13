package com.graduation.even.graduationclient.net.bean.request;

import java.util.List;

/**
 * Created by Even on 2017/11/13.
 */

public class SetMonitorRequest {
    private String token;
    private int userId;
    private String dptStation;
    private String arrStation;
    private long startDate;
    private List<String> trainNo;
    private List<String> seats;

    public SetMonitorRequest(String token, int userId, String dptStation, String arrStation, long startDate, List<String> trainNo, List<String> seats) {
        this.token = token;
        this.userId = userId;
        this.dptStation = dptStation;
        this.arrStation = arrStation;
        this.startDate = startDate;
        this.trainNo = trainNo;
        this.seats = seats;
    }
}
