package com.graduation.even.graduationclient.net.bean.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Even on 2017/11/9.
 */

public class TicketShowResponse extends BaseResponse {
    public Data data;

    public class Data{
        public int pageNumber;
        public boolean lastPage;
        public String dptStation;
        public String arrStation;
        public List<Ticket> list;
    }

    // 票信息
    public class Ticket{
        public String dptStationName;
        public String arrStationName;
        public String trainNo;
        public long startDate;
        public String dptTime;
        public String arrTime;
        public String interval;
        public String cheapestPrice;
        public SeatType seats;
        @Expose
        public boolean isSelected;
    }

    // 座位类别
    //zero:无座 one:一等座 two:二等座 business:商务座 soft:软卧 hard_sleep:硬卧 hard_seat:硬座
    public class SeatType{
        public SeatInfo zero;
        public SeatInfo one;
        public SeatInfo two;
        public SeatInfo business;
        public SeatInfo soft;
        @SerializedName("hard_sleep")
        public SeatInfo hardSleep;
        @SerializedName("hard_seat")
        public SeatInfo hardSeat;
    }

    // 座位信息
    public class SeatInfo{
        public double price;
        public int count;
    }
}
