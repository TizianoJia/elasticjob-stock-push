package cn.jxh.stock.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatetimeUtil {

    public final static String DATE_PATTERN = "yyyy-MM-dd";

    public final static String TIME_PATTERN = "HH:mm:ss";

    public final static String DATE_TIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    /**
     * String to Date
     *
     * @param DateString
     * @return
     * @throws ParseException
     */
    public static Date String2Date(String DateString, Date defValue) {
        DateFormat fmt = new SimpleDateFormat(DATE_PATTERN);
        try {
            return fmt.parse(DateString);
        } catch (ParseException e) {
            return defValue;
        }
    }

    /**
     * String to Datetime
     *
     * @param DatetimeString
     * @return
     * @throws ParseException
     */
    public static Date String2Datetime(String DatetimeString, Date defValue) {
        DateFormat fmt = new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            return fmt.parse(DatetimeString);
        } catch (ParseException e) {
            return defValue;
        }
    }

    public static Date getSimpleDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */

    public static int diffdates(Date date1, Date date2) {
        int result = 0;

        GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = new GregorianCalendar();

        gc1.setTime(date1);
        gc2.setTime(date2);
        result = getDays(gc1, gc2);

        return result;
    }

    public static int getDays(GregorianCalendar g1, GregorianCalendar g2) {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;

        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }

        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        gc1.clear(Calendar.MINUTE);
        gc1.clear(Calendar.HOUR_OF_DAY);

        gc2.clear(Calendar.MILLISECOND);
        gc2.clear(Calendar.SECOND);
        gc2.clear(Calendar.MINUTE);
        gc2.clear(Calendar.HOUR_OF_DAY);

        while (gc1.before(gc2)) {
            gc1.add(Calendar.DATE, 1);
            elapsed++;
        }
        return elapsed;
    }

}
