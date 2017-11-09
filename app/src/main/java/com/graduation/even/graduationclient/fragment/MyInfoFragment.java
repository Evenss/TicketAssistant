package com.graduation.even.graduationclient.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.activity.ChangePwdActivity;
import com.graduation.even.graduationclient.activity.LoginActivity;
import com.graduation.even.graduationclient.activity.MainActivity;
import com.graduation.even.graduationclient.net.callback.NetCallBack;
import com.graduation.even.graduationclient.net.connector.NetworkConnector;
import com.graduation.even.graduationclient.user.UserInfo;
import com.graduation.even.graduationclient.util.PLog;
import com.graduation.even.graduationclient.util.SharedPreferencesUtil;
import com.graduation.even.graduationclient.util.ToastUtil;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Even on 2017/11/6.
 */

public class MyInfoFragment extends BaseFragment implements View.OnClickListener{

    private RelativeLayout loginPromptRl,changePwdRl,orderInfoRl,moreRl,setEmailRl;
    private Button loginBtn,logoutBtn;
    private TextView phoneTv,emailTv;
    private UserInfo userInfo;

    private NetworkConnector mNetworkConnector;
    private SharedPreferencesUtil mSPUtil;
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
        setEmailRl = view.findViewById(R.id.rl_set_email);
        loginBtn = view.findViewById(R.id.btn_login);
        logoutBtn = view.findViewById(R.id.btn_logout);
        phoneTv = view.findViewById(R.id.tv_phone);
        changePwdRl = view.findViewById(R.id.rl_change_pwd);
        orderInfoRl = view.findViewById(R.id.rl_order_info);
        moreRl = view.findViewById(R.id.rl_more);
        emailTv = view.findViewById(R.id.tv_email);
    }

    @Override
    void initData() {
        mNetworkConnector = NetworkConnector.getInstance();
        mSPUtil = SharedPreferencesUtil.getInstance(getActivity());
    }

    @Override
    void initEvent() {
        loginBtn.setOnClickListener(this);
        setEmailRl.setOnClickListener(this);
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
            case R.id.rl_set_email:

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
                        // 返回的活动后取出用户数据
                        userInfo = UserInfo.getInstance();
                        phoneTv.setText(userInfo.getPhone());
                        if(TextUtils.isEmpty(userInfo.getEmail())){
                            emailTv.setText("暂未设置邮箱");
                            emailTv.setTextColor(ContextCompat.getColor(getActivity(),R.color.grayDark));
                        }else{
                            emailTv.setText(userInfo.getEmail());
                        }
                    }
                    break;
            }
        }
    }

    // todo 网络请求
    private void logout(){

        mNetworkConnector.logout(new NetCallBack() {
            @Override
            public void onNetworkError() {
                ToastUtil.showToastOnUIThread(getActivity(),"网络错误");
            }

            @Override
            public void onFailed(String error) {
                ToastUtil.showToastOnUIThread(getActivity(),error);
            }

            @Override
            public void onSuccess(Object object) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity) getActivity()).bakcToMain();
                        ToastUtil.showToast(getActivity(),"退出登录成功");
                    }
                });
            }
        });
    }

}
