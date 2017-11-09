package com.graduation.even.graduationclient.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Even on 2017/11/9.
 * 本地保存用户账号和密码
 */

public class SharedPreferencesUtil {
    private static SharedPreferencesUtil mInstance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private final static String TAG_USER_INFO = "user_info";
    private final static String TAG_USER_PHONE = "phone";
    private final static String TAG_USER_PWD = "password";

    private SharedPreferencesUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(TAG_USER_INFO, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static SharedPreferencesUtil getInstance(Context context) {
        mInstance = new SharedPreferencesUtil(context);
        return mInstance;
    }

    public void writePassword(String password) {
        mEditor.putString(TAG_USER_PWD, password);
        mEditor.apply();
    }

    public void writePhone(String phone) {
        mEditor.putString(TAG_USER_PHONE, phone);
        mEditor.apply();
    }

    public String getPassword() {
        return mSharedPreferences.getString(TAG_USER_PWD, "");
    }

    public String getPhone() {
        return mSharedPreferences.getString(TAG_USER_PHONE, "");
    }

    // 清空数据
    public void clearData() {
        mEditor.clear().apply();
        mEditor = null;
        mSharedPreferences = null;
        mInstance = null;
    }

}
