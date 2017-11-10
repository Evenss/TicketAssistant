package com.graduation.even.graduationclient.net.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Even on 2017/11/9.
 */

public class TicketShowResponse extends BaseResponse {
    @SerializedName("data")
    public Data data;

    public class Data{
        @SerializedName("pageNumber")
        public int pageNumber;
        @SerializedName("lastPage")
        public boolean lastPage;
        @SerializedName("dptStation")
        public String dptStation;
        @SerializedName("arrStation")
        public String arrStation;
        @SerializedName("list")
        public List<Ticket> list;
    }

    // 票信息
    public class Ticket{
        @SerializedName("dptStationName")
        public String dptStationName;
        @SerializedName("arrStationName")
        public String arrStationName;
        @SerializedName("trainNo")
        public String trainNo;
        @SerializedName("startDate")
        public long startDate;
        @SerializedName("dptTime")
        public String dptTime;
        @SerializedName("arrTime")
        public String arrTime;
        @SerializedName("interval")
        public String interval;
        @SerializedName("cheapestPrice")
        public String cheapestPrice;
        @SerializedName("seats")
        public SeatType seats;
    }

    // 座位类别
    //zero:无座 one:一等座 two:二等座 business:商务座 soft:软卧 hard_sleep:硬卧 hard_seat:硬座
    public class SeatType{
        @SerializedName("zero")
        public SeatInfo zero;
        @SerializedName("one")
        public SeatInfo one;
        @SerializedName("two")
        public SeatInfo two;
        @SerializedName("business")
        public SeatInfo business;
        @SerializedName("soft")
        public SeatInfo soft;
        @SerializedName("hard_sleep")
        public SeatInfo hardSleep;
        @SerializedName("hard_seat")
        public SeatInfo hardSeat;
    }

    // 座位信息
    public class SeatInfo{
        @SerializedName("price")
        public double price;
        @SerializedName("count")
        public int count;
    }
}
