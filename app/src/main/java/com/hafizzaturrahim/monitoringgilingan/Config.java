package com.hafizzaturrahim.monitoringgilingan;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PC-34 on 4/19/2017.
 */

public class Config {
    public static String base_url = "http://10.34.246.3/gilinganlocal";
//    public static String base_url = "http://gilingan.hol.es";

    public static String convertDate(String oldDate) {
        String newDate = null;
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        try {
            Date date = formatter.parse(oldDate);
            SimpleDateFormat newFormatter = new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss");
            newDate = newFormatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

}
