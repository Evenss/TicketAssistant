package com.graduation.even.graduationclient.net.bean.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Even on 2017/11/9.
 */

public class LogoutResponse extends BaseResponse {
    @SerializedName("data")
    public String data;
}
