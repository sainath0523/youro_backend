package com.youro.web.utils;

import java.text.SimpleDateFormat;

public class Constants {

    public static String dateData = "yyyy-mm-dd";
    public static String requestData = "E MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)";

    public static String timeData = "HH:mm:ss";

    public  static SimpleDateFormat inputFormat = new SimpleDateFormat(requestData);
    public  static  SimpleDateFormat dateFormat = new SimpleDateFormat(dateData);
    public  static  SimpleDateFormat timeFormat = new SimpleDateFormat(timeData);

}
