package com.graduation.even.graduationclient.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private static Toast toast;
    public static void showToast(Context context, String content){
        //判断toast是否为空
		/*
		*如果为空 则创建Toast
		*如果不为空 设置Toast内容
		*防止多次提醒
		*/
        if(toast == null){
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();
    }
}
