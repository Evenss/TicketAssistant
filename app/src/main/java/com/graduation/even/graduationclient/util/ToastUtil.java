package com.graduation.even.graduationclient.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private static Toast toast;

    public static void showToastOnUIThread(final Context context,final String content) {
        //判断toast是否为空
        /*
		*如果为空 则创建Toast
		*如果不为空 设置Toast内容
		*防止多次提醒
		*/
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
                } else {
                    toast.setText(content);
                }
                toast.show();
            }
        });
    }
    public static void showToast(final Context context,final String content) {
        //判断toast是否为空
        /*
		*如果为空 则创建Toast
		*如果不为空 设置Toast内容
		*防止多次提醒
		*/
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
