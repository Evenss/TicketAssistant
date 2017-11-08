package com.graduation.even.graduationclient.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Even on 2017/11/7.
 */
public class TimeUtilTest {
    @Test
    public void dateToStamp() throws Exception {
        String date = "2017年12月1日";
        long dateStamp = TimeUtil.dateToStamp(date,"yyyy年MM月dd日");
        System.out.println("dateStamp " + dateStamp);
        
        String date2;
        date2 = TimeUtil.getTimeFormatted(dateStamp/1000,"yyyy年MM月dd日");
        System.out.println("date " + date2);
    }

}