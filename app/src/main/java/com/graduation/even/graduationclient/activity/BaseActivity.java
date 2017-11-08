package com.graduation.even.graduationclient.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    private static Toast toast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceId());
        
        if (forceScreenOrientationPortrait()){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initView();
        initData();
        initEvent();
    }

    //是否要强制竖屏SCREEN_ORIENTATION_PORTRAIT
    abstract protected boolean forceScreenOrientationPortrait();

    //获取布局文件
    abstract protected int getResourceId();

    //初始化控件
    abstract protected void initView();

    //初始化数据
    abstract protected void initData();

    //初始化事件，给控件设置数据 adapter等等
    abstract protected void initEvent();

    //显示toast通知
    public void showToast(String content){
        if(toast == null){
            toast = Toast.makeText(BaseActivity.this, content, Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();
    }
}
