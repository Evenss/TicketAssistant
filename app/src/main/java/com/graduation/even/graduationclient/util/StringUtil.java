package com.graduation.even.graduationclient.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Even on 2017/11/1.
 * 各种字符串的工具类
 */

public class StringUtil {


    /* 电话号码是否合法 */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str);
    }

    private static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /* 邮箱是否合法 */
    private static final String REGEX_EMAIL =
            "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static boolean isEmailLegal(String email){
        return Pattern.matches(REGEX_EMAIL,email);
    }

    private static Random mRandom = new Random();
    /* 得到4位随机数字 */
    public static int getRandom4Num() {
        return getRandomNum(1000, 9999);
    }

    /* 得到随机数字 */
    public static int getRandomNum(int min, int max) {
        return mRandom.nextInt(max) % (max - min + 1) + min;
    }

}
