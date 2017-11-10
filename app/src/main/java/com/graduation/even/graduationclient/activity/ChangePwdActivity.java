package com.graduation.even.graduationclient.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.net.callback.NetCallBack;
import com.graduation.even.graduationclient.net.connector.NetworkConnector;
import com.graduation.even.graduationclient.util.MD5Util;
import com.graduation.even.graduationclient.util.SharedPreferencesUtil;
import com.graduation.even.graduationclient.util.ToastUtil;
import com.graduation.even.graduationclient.util.ToolbarUtil;

public class ChangePwdActivity extends BaseActivity {
    private EditText oldPwdEt, newPwdEt;
    private Button changePwdBtn;
    private Toolbar mToolbar;

    private NetworkConnector mNetworkConnector;
    private SharedPreferencesUtil mSPUtil;

    @Override
    protected boolean forceScreenOrientationPortrait() {
        return false;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView() {
        oldPwdEt = findViewById(R.id.et_old_pwd);
        newPwdEt = findViewById(R.id.et_new_pwd);
        changePwdBtn = findViewById(R.id.btn_change_pwd);
        mToolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void initData() {
        mNetworkConnector = NetworkConnector.getInstance();
        mSPUtil = SharedPreferencesUtil.getInstance(this);
        new ToolbarUtil().initToolbar(this, mToolbar);
    }

    @Override
    protected void initEvent() {
        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPwdStr = oldPwdEt.getText().toString();
                String newPwdStr = newPwdEt.getText().toString();
                if (TextUtils.isEmpty(oldPwdStr)) {
                    ToastUtil.showToast(ChangePwdActivity.this, "密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(newPwdStr)) {
                    ToastUtil.showToast(ChangePwdActivity.this, "密码不能为空");
                    return;
                }
                if (oldPwdStr.equals(newPwdStr)) {
                    ToastUtil.showToast(ChangePwdActivity.this, "新密码不能与旧密码相同！");
                    return;
                }
                changePwd(oldPwdStr, newPwdStr);
            }
        });
    }

    private void changePwd(String oldPwd, final String newPwd) {
        final String oldPwdMD5 = MD5Util.encoderByMd5(oldPwd);
        final String newPwdMD5 = MD5Util.encoderByMd5(newPwd);
        mNetworkConnector.changePwd(oldPwdMD5, newPwdMD5, new NetCallBack() {
            @Override
            public void onTokenInvalid() {
                ToastUtil.showToastOnUIThread(ChangePwdActivity.this, "登录信息已过期，请重新登录");
            }

            @Override
            public void onNetworkError() {
                ToastUtil.showToastOnUIThread(ChangePwdActivity.this, "网络错误");
            }

            @Override
            public void onFailed(String error) {
                ToastUtil.showToastOnUIThread(ChangePwdActivity.this, error);
            }

            @Override
            public void onSuccess(Object object) {
                ToastUtil.showToastOnUIThread(ChangePwdActivity.this, "修改密码成功");
                mSPUtil.writePassword(newPwdMD5);
            }
        });
    }
}
