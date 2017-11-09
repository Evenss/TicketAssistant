package com.graduation.even.graduationclient.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.activity.ChangePwdActivity;
import com.graduation.even.graduationclient.activity.LoginActivity;
import com.graduation.even.graduationclient.activity.MainActivity;
import com.graduation.even.graduationclient.util.PLog;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Even on 2017/11/6.
 */

public class MyInfoFragment extends BaseFragment implements View.OnClickListener{

    private RelativeLayout loginPromptRl,changePwdRl,orderInfoRl,moreRl;
    private Button loginBtn,logoutBtn;
    private TextView phoneTv;

    private final static int LOGIN_REQUEST_CODE = 1;
    @Override
    int getResourceId() {
        return R.layout.fragment_my_info;
    }

    @Override
    void initConfigure() {

    }

    @Override
    void initView(View view) {
        loginPromptRl = view.findViewById(R.id.rl_login_prompt);
        loginBtn = view.findViewById(R.id.btn_login);
        logoutBtn = view.findViewById(R.id.btn_logout);
        phoneTv = view.findViewById(R.id.tv_phone);
        changePwdRl = view.findViewById(R.id.rl_change_pwd);
        orderInfoRl = view.findViewById(R.id.rl_order_info);
        moreRl = view.findViewById(R.id.rl_more);
    }

    @Override
    void initData() {

    }

    @Override
    void initEvent() {
        loginBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        changePwdRl.setOnClickListener(this);
        orderInfoRl.setOnClickListener(this);
        moreRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent,LOGIN_REQUEST_CODE);
                break;
            case R.id.btn_logout:
                logout();
                break;
            case R.id.rl_change_pwd:
                intent = new Intent(getActivity(), ChangePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_order_info:
                break;
            case R.id.rl_more:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            PLog.i("onActivityResult RESULT_OK");
            switch (requestCode){
                case LOGIN_REQUEST_CODE:
                    PLog.i("LOGIN_REQUEST_CODE");
                    if(data != null && data.getBooleanExtra("isLogin",false)){
                        loginPromptRl.setVisibility(View.GONE);
                        // todo 从返回的活动中取出用户数据
                    }
                    break;
            }
        }
    }

    // todo 网络请求
    private void logout(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((MainActivity) getActivity()).backToLogin();
            }
        });
    }

}
