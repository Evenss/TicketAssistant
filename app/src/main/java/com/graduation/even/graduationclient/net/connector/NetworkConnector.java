package com.graduation.even.graduationclient.net.connector;

import com.google.gson.Gson;
import com.graduation.even.graduationclient.constant.API;
import com.graduation.even.graduationclient.net.bean.request.LoginRequest;
import com.graduation.even.graduationclient.net.bean.response.LoginResponse;
import com.graduation.even.graduationclient.net.callback.NetCallBack;
import com.graduation.even.graduationclient.user.UserInfo;
import com.graduation.even.graduationclient.util.MD5Util;
import com.graduation.even.graduationclient.util.PLog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Even on 2017/11/8.
 */

public class NetworkConnector {
    private static NetworkConnector mNetworkConnector;

    private OkHttpClient mClient;
    private MediaType JSON, FILE, JPG;
    private Gson mGson;
    private UserInfo mUserInfo;

    public static NetworkConnector getInstance() {
        if (null == mNetworkConnector) {
            mNetworkConnector = new NetworkConnector();
        }
        return mNetworkConnector;
    }

    private NetworkConnector() {
        PLog.i("network connector is created");
        mClient = new OkHttpClient();
        mGson = new Gson();
        JSON = MediaType.parse("application/json; charset=utf-8");
        FILE = MediaType.parse("application/octet-stream");
        JPG = MediaType.parse("image/jpg");
        mUserInfo = UserInfo.getInstance();
    }

    public void login(String phone, String pwd, final NetCallBack callBack) {
        PLog.i("login, url is " + API.URL_LOGIN);
        pwd = MD5Util.encoderByMd5(pwd);
        PLog.i("encoded password " + pwd);
        LoginRequest loginRequest = new LoginRequest(phone, pwd);
        RequestBody body = RequestBody.create(JSON, mGson.toJson(loginRequest));
        Request request = new Request.Builder()
                .url(API.URL_LOGIN)
                .post(body)
                .build();
        Call call = mClient.newCall(request);
        PLog.i("do enqueue");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PLog.i("failed to login" + e);
                callBack.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                PLog.i("login, response is " + string);
                LoginResponse loginResponse = mGson.fromJson(string, LoginResponse.class);
                if (loginResponse.isSuccess()) {
                    mUserInfo.setInfo(loginResponse.data.userId, loginResponse.data.token, loginResponse.data.email, loginResponse.data.invalidTime);
                    callBack.onSuccess(null);
                } else
                    callBack.onFailed(loginResponse.error);
            }
        });
    }
}
