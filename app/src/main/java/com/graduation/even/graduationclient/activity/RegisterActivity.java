package com.graduation.even.graduationclient.activity;

import android.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.util.ToastUtil;
import com.graduation.even.graduationclient.util.ToolbarUtil;

import static com.graduation.even.graduationclient.util.StringUtil.getRandom4Num;
import static com.graduation.even.graduationclient.util.StringUtil.isPhoneLegal;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private Button getCodeBtn;
    private Button registerBtn;
    private EditText checkCodeEt;
    private EditText confirmPwdEt;
    private EditText phoneEt;
    private EditText setPwdEt;
    private String mCode;

    @Override
    protected boolean forceScreenOrientationPortrait() {
        return false;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        getCodeBtn = findViewById(R.id.btn_get_code);
        registerBtn = findViewById(R.id.btn_register);
        checkCodeEt =  findViewById(R.id.et_check_code);
        confirmPwdEt =  findViewById(R.id.et_confirm_pwd);
        phoneEt =  findViewById(R.id.et_phone);
        setPwdEt =  findViewById(R.id.et_set_pwd);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        getCodeBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        new ToolbarUtil().initToolbar(this,mToolbar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get_code:
                int code = getRandom4Num();
                mCode = String.valueOf(code);
                showCodeDialog(mCode);
                break;
            case R.id.btn_register:
                String phone = phoneEt.getText().toString();
                String pwd = setPwdEt.getText().toString();
                if(!isPhoneLegal(phone)){
                    phoneEt.setError("输入手机号不合法！");
                    return;
                }
                if(checkCodeEt.getText().toString() != mCode){
                    checkCodeEt.setError("验证码不正确！");
                    return;
                }
                if(confirmPwdEt.getText().toString() != pwd){
                    confirmPwdEt.setError("两次输入密码不一致！");
                    return;
                }
                registerAccount(phone,pwd);
                break;
        }
    }

    private void showCodeDialog(String code){
        /*
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         */
        final AlertDialog.Builder dialog =
                new AlertDialog.Builder(this);
        dialog.setTitle("验证码");
        dialog.setMessage(code);
        dialog.setPositiveButton("我已记住", null);
        dialog.setCancelable(false);
        dialog.show();
    }

    // todo
    /* 账号注册 */
    private void registerAccount(String phone,String pwd){

    }
}
