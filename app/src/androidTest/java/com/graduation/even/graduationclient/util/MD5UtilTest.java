package com.graduation.even.graduationclient.util;

import org.junit.Test;


/**
 * Created by Even on 2017/11/8.
 */
public class MD5UtilTest {
    @Test
    public void encoderByMd5() throws Exception {
        String pwd = "123456";
        String pwdMD5 = MD5Util.encoderByMd5(pwd);
        PLog.i("pwdMD5 = " + pwdMD5);
    }

}