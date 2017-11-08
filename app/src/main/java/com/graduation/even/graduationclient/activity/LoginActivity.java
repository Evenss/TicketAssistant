package com.graduation.even.graduationclient.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.util.ToolbarUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEt;
    private EditText pwdEt;
    private Button loginBtn;
    private TextView registerBtn;
    private Toolbar mToolbar;

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
    }

    @Override
    protected void initData() {

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
                login(phone, pwd);
                break;
        }
    }

    // todo
    /* 登录网络请求 */
    private void login(String phone, String pwd) {
        Intent intent = getIntent();
        intent.putExtra("isLogin",true);
        setResult(RESULT_OK,intent);
        finish();
    }

}
