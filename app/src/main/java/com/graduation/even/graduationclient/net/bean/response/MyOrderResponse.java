package com.graduation.even.graduationclient.net.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Even on 2017/11/13.
 */

public class MyOrderResponse extends BaseResponse {
    @SerializedName("data")
    public Data data;

    public class Data{
        @SerializedName("pageNumber")
        public int pageNumber;
        @SerializedName("lastPage")
        public boolean lastPage;
        @SerializedName("list")
        public List<Order> list;
    }

    public class Order{
        @SerializedName("dptStationName")
        public String dptStationName;
        @SerializedName("arrStationName")
        public String arrStationName;
        @SerializedName("trainNo")
        public String trainNo;
        @SerializedName("state") // 订单状态 0-已完成 1-票量监控中
        public int state;
        @SerializedName("startDate")
        public long startDate;
        @SerializedName("seats")
        public List<String> seats;
        @SerializedName("price")
        public double price;
    }
}
