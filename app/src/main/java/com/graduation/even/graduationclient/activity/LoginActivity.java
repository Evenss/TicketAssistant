package com.graduation.even.graduationclient.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.net.bean.response.LoginResponse;
import com.graduation.even.graduationclient.net.callback.NetCallBack;
import com.graduation.even.graduationclient.net.connector.NetworkConnector;
import com.graduation.even.graduationclient.util.PLog;
import com.graduation.even.graduationclient.util.ToolbarUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEt;
    private EditText pwdEt;
    private Button loginBtn;
    private TextView registerBtn;
    private Toolbar mToolbar;
    private ProgressBar progressBar;

    private NetworkConnector mNetworkConnector;
    private boolean isLogingin = false;
    @Override
    protected boolean forceScreenOrientationPortrait() {
        return false;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        phoneEt = (EditText) findViewById(R.id.et_phone);
        pwdEt = (EditText) findViewById(R.id.et_pwd);
        loginBtn = (Button) findViewById(R.id.btn_login);
        registerBtn = (TextView) findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    protected void initData() {
        mNetworkConnector = NetworkConnector.getInstance();
    }

    @Override
    protected void initEvent() {
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        new ToolbarUtil().initToolbar(this,mToolbar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                String phone = phoneEt.getText().toString();
                String pwd = pwdEt.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    PLog.w("phone is empty");
                    phoneEt.setError("账户不能为空");
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    PLog.w("pwd is empty");
                    pwdEt.setError("密码不能为空");
                    return;
                }
                attemptLogin(phone, pwd);
                break;
        }
    }

    // todo
    /* 登录网络请求 */
    private void attemptLogin(String phone, String pwd) {

        if (isLogingin)
            return;
        else
            isLogingin = true;

        showProgress(true);
        mNetworkConnector.login(phone, pwd, new NetCallBack() {
            @Override
            public void onNetworkError() {

            }

            @Override
            public void onFailed(String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgress(false);
                        isLogingin = false;
                    }
                });
            }

            @Override
            public void onSuccess(Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgress(false);
                        isLogingin = false;
                        //todo 向本地文件中写入数据

                        // 开启下一个活动
                        Intent intent = getIntent();
                        intent.putExtra("isLogin",true);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });

            }
        });
    }

    private void showProgress(boolean show) {
        if (show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

}
