package com.graduation.even.graduationclient.constant;

/**
 * Created by Even on 2017/11/3.
 */

public class API {
    private static String HOST = "http://192.168.3.24:8080";
    public static String URL_LOGIN = HOST + "/user/login";
    public static String URL_LOGOUT = HOST + "/user/logout";
    public static String URL_REGISTER = HOST + "/user/register";
    public static String URL_SET_EMAIL = HOST + "/user/set_email";
    public static String URL_CHANGE_PWD = HOST + "/user/change_pwd";
    public static String URL_TICKET_QUERY = HOST + "/ticket/query";
    public static String URL_SET_MONITOR = HOST + "/ticket/set_monitor";
    public static String URL_MY_ORDER = HOST + "/ticket/my_order";
}
