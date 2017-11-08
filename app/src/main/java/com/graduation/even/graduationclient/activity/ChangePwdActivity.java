package com.graduation.even.graduationclient.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.util.MD5Util;
import com.graduation.even.graduationclient.util.ToastUtil;

public class ChangePwdActivity extends BaseActivity {
    private EditText oldPwdEt, newPwdEt;
    private Button changePwdBtn;

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
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPwdStr = oldPwdEt.getText().toString();
                String newPwdStr = newPwdEt.getText().toString();
                if (TextUtils.isEmpty(oldPwdStr) || TextUtils.isEmpty(newPwdStr)) {
                    ToastUtil.showToast(ChangePwdActivity.this, "密码不能为空");
                    return;
                }
                changePwd(oldPwdStr,newPwdStr);
            }
        });
    }

    //todo
    private void changePwd(String oldPwd,String newPwd) {
        oldPwd = MD5Util.encoderByMd5(oldPwd);
        newPwd = MD5Util.encoderByMd5(newPwd);


    }
}
