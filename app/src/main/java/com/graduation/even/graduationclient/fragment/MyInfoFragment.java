package com.graduation.even.graduationclient.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import static com.graduation.even.graduationclient.util.StringUtil.isEmailLegal;

/**
 * Created by Even on 2017/11/6.
 */

public class MyInfoFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout loginPromptRl, changePwdRl, orderInfoRl, moreRl, setEmailRl;
    private Button loginBtn, logoutBtn;
    private TextView phoneTv, emailTv;
    private UserInfo mUserInfo;

    private NetworkConnector mNetworkConnector;
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
        mUserInfo = UserInfo.getInstance();
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
        switch (view.getId()) {
            case R.id.btn_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
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
                createEmailDialog();
                break;
            case R.id.rl_more:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            PLog.i("onActivityResult RESULT_OK");
            switch (requestCode) {
                case LOGIN_REQUEST_CODE:
                    PLog.i("LOGIN_REQUEST_CODE");
                    if (data != null && data.getBooleanExtra("isLogin", false)) {
                        loginPromptRl.setVisibility(View.GONE);
                        // 返回的活动后取出用户数据
                        phoneTv.setText(mUserInfo.getPhone());
                        if (TextUtils.isEmpty(mUserInfo.getEmail())) {
                            emailTv.setText("暂未设置邮箱");
                            emailTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.grayDark));
                        } else {
                            emailTv.setText(mUserInfo.getEmail());
                        }
                    }
                    break;
            }
        }
    }

    private void createEmailDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("填写联系邮箱");
        builder.setCancelable(false);
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_email, null);
        builder.setView(view);
        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", null);

        final EditText email = (EditText) view.findViewById(R.id.email);
        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PLog.i("EmailDialog click Positive Button");
                final String emailStr = email.getText().toString().trim();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isEmailLegal(emailStr)) {
                            PLog.i("Legal");
                            mUserInfo.setEmail(emailStr);
                            setEmail(emailStr);
                            dialog.dismiss();
                        } else {
                            PLog.i("not Legal");
                            email.setError("输入邮箱不合法！");
                        }
                    }
                });

            }
        });
    }

    // 登录
    private void logout() {
        mNetworkConnector.logout(new NetCallBack() {
            @Override
            public void onNetworkError() {
                ToastUtil.showToastOnUIThread(getActivity(), "网络错误");
            }

            @Override
            public void onFailed(String error) {
                ToastUtil.showToastOnUIThread(getActivity(), error);
            }

            @Override
            public void onSuccess(Object object) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity) getActivity()).bakcToMain();
                        ToastUtil.showToast(getActivity(), "退出登录成功");
                    }
                });
            }
        });
    }

    //设置邮箱
    private void setEmail(final String email) {
        mNetworkConnector.setEmail(email, new NetCallBack() {
            @Override
            public void onNetworkError() {
                ToastUtil.showToastOnUIThread(getActivity(), "网络错误");
            }

            @Override
            public void onFailed(String error) {
                ToastUtil.showToastOnUIThread(getActivity(), error);
            }

            @Override
            public void onSuccess(Object object) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(getActivity(), "设置邮箱成功！");
                        emailTv.setText(email);
                    }
                });

            }
        });
    }

}
