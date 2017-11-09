package com.graduation.even.graduationclient.net.bean.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Even on 2017/11/9.
 */

public class RegisterResponse extends BaseResponse {
    @SerializedName("data")
    public LoginResponse.Data data;

    public class Data{
        @SerializedName("user_id")
        public int userId;
        @SerializedName("phone")
        public String phone;
        @SerializedName("token")
        public String token;
        @SerializedName("invalid_time")
        public int invalidTime;

        public Data(int userId, String phone, String token, int invalidTime) {
            this.userId = userId;
            this.phone = phone;
            this.token = token;
            this.invalidTime = invalidTime;
        }
    }
}
