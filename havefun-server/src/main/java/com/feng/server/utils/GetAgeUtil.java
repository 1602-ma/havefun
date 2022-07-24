package com.feng.server.utils;

import org.joda.time.DateTime;
import org.joda.time.Years;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;

/**
 * 根据出生日期获取年龄
 * @author f
 * @date 2022/7/24 11:41
 */
public class GetAgeUtil {

    public static int getAge(String yearMonthDay) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = sdf.parse(yearMonthDay);
            Years years = Years.yearsBetween(new DateTime(birthday), DateTime.now());
            return years.getYears();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
