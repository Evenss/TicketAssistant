package com.graduation.even.graduationclient.net.bean.request;

/**
 * Created by Even on 2017/11/9.
 */

public class TicketShowRequest {
    private String departure;
    private String destination;
    private long date;
    private boolean isGD;
    private int pageSize;
    private int pageNumber;

    public TicketShowRequest(String departure, String destination, long date, boolean isGD, int pageSize, int pageNumber) {
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.isGD = isGD;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }
}
