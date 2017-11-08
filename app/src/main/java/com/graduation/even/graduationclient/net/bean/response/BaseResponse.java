package com.graduation.even.graduationclient.net.bean.response;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Even on 2017/11/8.
 */

public class BaseResponse {
    @SerializedName("state")
    public int state; // 0:success 1:error

    @SerializedName("error")
    public String error;

    public Object data;

    public boolean isSuccess() {
        return state == 0;
    }
}
