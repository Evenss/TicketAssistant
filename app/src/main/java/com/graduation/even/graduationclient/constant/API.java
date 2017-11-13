package com.graduation.even.graduationclient.constant;

/**
 * Created by Even on 2017/11/3.
 */

public class API {
    //URL
    private static final String URL_START = "http://train.qunar.com/dict/open/s2s.do?dptStation=";
    private static final String URL_END = "&arrStation=";
    private static final String URL_DATE = "&date=";
    private static final String URL_TYPE = "&type=normal&user=neibu&source=site&start=1&num=500&sort=3";


    private static String HOST = "http://";
    public static String URL_LOGIN = HOST + "/user/login";
    public static String URL_LOGOUT = HOST + "/user/logout";
    public static String URL_REGISTER = HOST + "/user/register";
    public static String URL_SET_EMAIL = HOST + "/user/set_email";
    public static String URL_CHANGE_PWD = HOST + "/user/change_pwd";
    public static String URL_TICKET_QUERY = HOST + "/ticket/query";
    public static String URL_SET_MONITOR = HOST + "/ticket/set_monitor";
    public static String URL_MY_ORDER = HOST + "/ticket/my_order";
}
