package com.graduation.even.graduationclient.net.bean.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Even on 2017/11/8.
 */

public class LoginResponse extends BaseResponse {

    @SerializedName("data")
    public Data data;

    public class Data{
        @SerializedName("user_id")
        public int userId;
        @SerializedName("phone")
        public String phone;
        @SerializedName("email")
        public String email;
        @SerializedName("token")
        public String token;
        @SerializedName("invalid_time")
        public int invalidTime;

        public Data(int userId, String phone, String email, String token, int invalidTime) {
            this.userId = userId;
            this.phone = phone;
            this.email = email;
            this.token = token;
            this.invalidTime = invalidTime;
        }
    }
}
