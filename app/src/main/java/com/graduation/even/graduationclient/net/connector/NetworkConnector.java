package com.graduation.even.graduationclient.net.connector;

import com.google.gson.Gson;
import com.graduation.even.graduationclient.constant.API;
import com.graduation.even.graduationclient.net.bean.request.ChangePwdRequest;
import com.graduation.even.graduationclient.net.bean.request.LoginRequest;
import com.graduation.even.graduationclient.net.bean.request.LogoutRequest;
import com.graduation.even.graduationclient.net.bean.request.MyOrderRequest;
import com.graduation.even.graduationclient.net.bean.request.RegisterRequest;
import com.graduation.even.graduationclient.net.bean.request.SetEmailRequest;
import com.graduation.even.graduationclient.net.bean.request.SetMonitorRequest;
import com.graduation.even.graduationclient.net.bean.request.TicketShowRequest;
import com.graduation.even.graduationclient.net.bean.response.ChangePwdResponse;
import com.graduation.even.graduationclient.net.bean.response.LoginResponse;
import com.graduation.even.graduationclient.net.bean.response.LogoutResponse;
import com.graduation.even.graduationclient.net.bean.response.MyOrderResponse;
import com.graduation.even.graduationclient.net.bean.response.RegisterResponse;
import com.graduation.even.graduationclient.net.bean.response.SetEmailResponse;
import com.graduation.even.graduationclient.net.bean.response.SetMonitorResponse;
import com.graduation.even.graduationclient.net.bean.response.TicketShowResponse;
import com.graduation.even.graduationclient.net.callback.NetCallBack;
import com.graduation.even.graduationclient.user.UserInfo;
import com.graduation.even.graduationclient.util.MD5Util;
import com.graduation.even.graduationclient.util.PLog;

import java.io.IOException;
import java.util.List;

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

    // 登录
    public void login(String phone, String pwd, final NetCallBack callBack) {
        PLog.i("login, url is " + API.URL_LOGIN);

        if (mUserInfo.isTokenInvalid()) {
            PLog.e("user token is invalid");
            callBack.onTokenInvalid();
            return;
        }

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
                PLog.i("failed to login:" + e);
                callBack.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                PLog.i("login, response is " + string);
                LoginResponse loginResponse = mGson.fromJson(string, LoginResponse.class);
                if (loginResponse.isSuccess()) {
                    PLog.i("success to login");
                    mUserInfo.setInfo(loginResponse.data.userId, loginResponse.data.token,
                            loginResponse.data.phone, loginResponse.data.email,
                            loginResponse.data.invalidTime);
                    callBack.onSuccess(null);
                } else {
                    PLog.i("failed to login:" + loginResponse.error);
                    callBack.onFailed(loginResponse.error);
                }
            }
        });
    }

    // 注册
    public void register(String phone, String pwd, final NetCallBack callBack) {
        PLog.i("register, url is " + API.URL_REGISTER);

        if (mUserInfo.isTokenInvalid()) {
            PLog.e("user token is invalid");
            callBack.onTokenInvalid();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(phone, pwd);
        RequestBody body = RequestBody.create(JSON, mGson.toJson(registerRequest));
        Request request = new Request.Builder()
                .url(API.URL_REGISTER)
                .post(body)
                .build();

        Call call = mClient.newCall(request);
        PLog.i("do enqueue");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PLog.i("failed to register:" + e);
                callBack.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                PLog.i("register, response is " + string);
                RegisterResponse registerResponse = mGson.fromJson(string, RegisterResponse.class);
                if (registerResponse.isSuccess()) {
                    PLog.i("success to register");
                    mUserInfo.setInfo(registerResponse.data.userId, registerResponse.data.token,
                            registerResponse.data.phone, registerResponse.data.invalidTime);
                    callBack.onSuccess(null);
                } else {
                    PLog.i("failed to register:" + registerResponse.error);
                    callBack.onFailed(registerResponse.error);
                }
            }
        });
    }

    //登出
    public void logout(final NetCallBack callBack) {
        PLog.i("logout, url is " + API.URL_LOGOUT);

        if (mUserInfo.isTokenInvalid()) {
            PLog.e("user token is invalid");
            callBack.onTokenInvalid();
            return;
        }

        String token = UserInfo.getInstance().getToken();
        LogoutRequest logoutRequest = new LogoutRequest(token);
        RequestBody body = RequestBody.create(JSON, mGson.toJson(logoutRequest));
        Request request = new Request.Builder()
                .url(API.URL_LOGOUT)
                .post(body)
                .build();

        Call call = mClient.newCall(request);
        PLog.i("do enqueue");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PLog.i("failed to logout:" + e);
                callBack.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                PLog.i("logout, response is " + string);
                LogoutResponse logoutResponse = mGson.fromJson(string, LogoutResponse.class);
                if (logoutResponse.isSuccess()) {
                    PLog.i("success to logout");
                    callBack.onSuccess(null);
                } else {
                    PLog.i("failed to logout:" + logoutResponse.error);
                    callBack.onFailed(logoutResponse.error);
                }
            }
        });
    }

    //设置邮箱
    public void setEmail(String email, final NetCallBack callBack) {
        PLog.i("set email, url is " + API.URL_SET_EMAIL);

        if (mUserInfo.isTokenInvalid()) {
            PLog.e("user token is invalid");
            callBack.onTokenInvalid();
            return;
        }

        String token = UserInfo.getInstance().getToken();
        int userId = UserInfo.getInstance().getUserId();
        SetEmailRequest setEmailRequest = new SetEmailRequest(token, userId, email);
        RequestBody body = RequestBody.create(JSON, mGson.toJson(setEmailRequest));
        Request request = new Request.Builder()
                .url(API.URL_SET_EMAIL)
                .post(body)
                .build();

        Call call = mClient.newCall(request);
        PLog.i("do enqueue");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PLog.i("failed to set email:" + e);
                callBack.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                PLog.i("set email, response is " + string);
                SetEmailResponse setEmailResponse = mGson.fromJson(string, SetEmailResponse.class);
                if (setEmailResponse.isSuccess()) {
                    PLog.i("success to set email");
                    callBack.onSuccess(null);
                } else {
                    PLog.i("failed to set email:" + setEmailResponse.error);
                    callBack.onFailed(setEmailResponse.error);
                }
            }
        });
    }

    //修改密码
    public void changePwd(String oldPwd, String newPwd, final NetCallBack callBack) {
        PLog.i("change pwd, url is " + API.URL_CHANGE_PWD);

        if (mUserInfo.isTokenInvalid()) {
            PLog.e("user token is invalid");
            callBack.onTokenInvalid();
            return;
        }

        String token = UserInfo.getInstance().getToken();
        int userId = UserInfo.getInstance().getUserId();
        ChangePwdRequest changePwdRequest = new ChangePwdRequest(token, userId, oldPwd, newPwd);

        RequestBody body = RequestBody.create(JSON, mGson.toJson(changePwdRequest));
        Request request = new Request.Builder()
                .url(API.URL_CHANGE_PWD)
                .post(body)
                .build();

        Call call = mClient.newCall(request);
        PLog.i("do enqueue");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PLog.i("failed to change pwd:" + e);
                callBack.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                PLog.i("change pwd, response is " + string);
                ChangePwdResponse changePwdResponse = mGson.fromJson(string, ChangePwdResponse.class);
                if (changePwdResponse.isSuccess()) {
                    PLog.i("success to change pwd");
                    callBack.onSuccess(null);
                } else {
                    PLog.i("failed to change pwd:" + changePwdResponse.error);
                    callBack.onFailed(changePwdResponse.error);
                }
            }
        });
    }

    // 获取票列表
    public void getTicketList(String departure, String destination, long date, boolean isGD,
                              int pageSize, int pageNumber, final NetCallBack callBack) {
        PLog.i("get ticket list, url is " + API.URL_TICKET_QUERY);

        if (mUserInfo.isTokenInvalid()) {
            PLog.e("user token is invalid");
            callBack.onTokenInvalid();
            return;
        }

        TicketShowRequest ticketRequest =
                new TicketShowRequest(departure, destination, date, isGD, pageSize, pageNumber);

        RequestBody body = RequestBody.create(JSON, mGson.toJson(ticketRequest));
        Request request = new Request.Builder()
                .url(API.URL_TICKET_QUERY)
                .post(body)
                .build();

        Call call = mClient.newCall(request);
        PLog.i("do enqueue");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PLog.i("failed to get ticket list:" + e);
                callBack.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                PLog.i("get ticket list, response is " + string);
                TicketShowResponse ticketResponse = mGson.fromJson(string, TicketShowResponse.class);
                if (ticketResponse.isSuccess()) {
                    PLog.i("success to get ticket list");
                    callBack.onSuccess(ticketResponse.data);
                } else {
                    PLog.i("failed to get ticket list:" + ticketResponse.error);
                    callBack.onFailed(ticketResponse.error);
                }
            }
        });
    }

    // 设置票余量监控
    public void setMonitor(String dptStation, String arrStation, long startDate,
                           List<String> trainNo, List<String> seats, final NetCallBack callBack) {
        PLog.i("set monitor, url is " + API.URL_SET_MONITOR);

        if (mUserInfo.isTokenInvalid()) {
            PLog.e("user token is invalid");
            callBack.onTokenInvalid();
            return;
        }

        String token = UserInfo.getInstance().getToken();
        int userId = UserInfo.getInstance().getUserId();
        SetMonitorRequest setMonitorRequest =
                new SetMonitorRequest(token, userId, dptStation, arrStation, startDate, trainNo, seats);

        RequestBody body = RequestBody.create(JSON, mGson.toJson(setMonitorRequest));
        Request request = new Request.Builder()
                .url(API.URL_SET_MONITOR)
                .post(body)
                .build();

        Call call = mClient.newCall(request);
        PLog.i("do enqueue");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PLog.i("failed to set monitor:" + e);
                callBack.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                PLog.i("set monitor, response is " + string);
                SetMonitorResponse setMonitorResponse = mGson.fromJson(string, SetMonitorResponse.class);
                if (setMonitorResponse.isSuccess()) {
                    PLog.i("success to set monitor");
                    callBack.onSuccess(null);
                } else {
                    PLog.i("failed to set monitor:" + setMonitorResponse.error);
                    callBack.onFailed(setMonitorResponse.error);
                }
            }
        });
    }

    // 用户标记车次查询
    public void getOrderList(int pageSize, int pageNumber, final NetCallBack callBack) {
        PLog.i("get order, url is " + API.URL_MY_ORDER);

        if (mUserInfo.isTokenInvalid()) {
            PLog.e("user token is invalid");
            callBack.onTokenInvalid();
            return;
        }

        String token = UserInfo.getInstance().getToken();
        int userId = UserInfo.getInstance().getUserId();
        MyOrderRequest myOrderRequest = new MyOrderRequest(token, userId, pageSize, pageNumber);

        RequestBody body = RequestBody.create(JSON, mGson.toJson(myOrderRequest));
        Request request = new Request.Builder()
                .url(API.URL_MY_ORDER)
                .post(body)
                .build();

        Call call = mClient.newCall(request);
        PLog.i("do enqueue");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PLog.i("failed to get order:" + e);
                callBack.onNetworkError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                PLog.i("get order, response is " + string);
                MyOrderResponse myOrderResponse = mGson.fromJson(string, MyOrderResponse.class);
                if (myOrderResponse.isSuccess()) {
                    PLog.i("success to get order");
                    callBack.onSuccess(myOrderResponse.data);
                } else {
                    PLog.i("failed to get order:" + myOrderResponse.error);
                    callBack.onFailed(myOrderResponse.error);
                }
            }
        });
    }
}
