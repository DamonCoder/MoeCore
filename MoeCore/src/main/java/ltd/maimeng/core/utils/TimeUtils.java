package ltd.maimeng.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by garfield on 6/28/16.
 */
public class TimeUtils {

    private static final int TIME_UNIT_SECOND = 1;
    private static final int TIME_UNIT_MINUTIUE = TIME_UNIT_SECOND * 60;//分钟
    private static final int TIME_UNIT_HOUR = TIME_UNIT_MINUTIUE * 60;//小时
    private static final int TIME_UNIT_DAY = TIME_UNIT_HOUR * 24;//天
    private static final int TIME_UNIT_YEAR = TIME_UNIT_DAY * 365;//年

    public static String showTime(long elapsed) {
        String time = "1分钟前";
        if (elapsed > TIME_UNIT_DAY) {
            return String.format(Locale.CHINA, "%d天前", (int) (elapsed / TIME_UNIT_DAY));
        }

        if (elapsed > TIME_UNIT_HOUR) {
            return String.format(Locale.CHINA, "%d小时前", (int) (elapsed / TIME_UNIT_HOUR));
        }

        if (elapsed > TIME_UNIT_MINUTIUE) {
            return String.format(Locale.CHINA, "%d分钟前", (int) (elapsed / TIME_UNIT_MINUTIUE));
        }

        return time;
    }

    public static String showGameTime(long elapsed) {
        String time = "1分钟前";
        if (elapsed > TIME_UNIT_DAY) {
            return String.format(Locale.CHINA, "%d天", (int) (elapsed / TIME_UNIT_DAY));
        }

        if (elapsed > TIME_UNIT_HOUR) {
            return String.format(Locale.CHINA, "%d小时", (int) (elapsed / TIME_UNIT_HOUR));
        }

        if (elapsed > TIME_UNIT_MINUTIUE) {
            return String.format(Locale.CHINA, "%d分钟", (int) (elapsed / TIME_UNIT_MINUTIUE));
        }

        return time;
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param time
     * @return
     */
    public static String getDateToString(long time) {
        time = time / 1000;
        if (time < 60) {
            return Math.max(0, time) + "秒";
        } else if (time > 60 && time < 3600) {
            int m = (int) (time / 60);
            int s = (int) (time % 60);
            return m + "分钟" + s + "秒";
        } else if (time >= 3600 && time < 86400) {
            int h = (int) (time / 3600);
            int m = (int) (time % 3600 / 60);
            int s = (int) (time % 3600 % 60 % 60);
            return h + "小时" + m + "分钟" + s + "秒";
        } else {
            int d = (int) (time / 86400);
            int h = (int) (time % 86400 / 3600);
            int m = (int) (time % 86400 % 3600 / 60);
            int s = (int) (time % 86400 % 3600 % 60 % 60);
            return d + "天" + h + "小时" + m + "分钟" + s + "秒";
        }
    }

    //年月日时分
    private static SimpleDateFormat yMdHm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    //年月日时分秒
    private static SimpleDateFormat yMdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //年月日
    private static SimpleDateFormat yMd = new SimpleDateFormat("yyyy-MM-dd");
    //月日时分
    private static SimpleDateFormat MdHm = new SimpleDateFormat("MM-dd HH:mm");
    //24小时制时分
    private static SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm");
    //12小时制时分
    private static SimpleDateFormat hourminuteFormat = new SimpleDateFormat("hh:mm");
    //月日
    private static SimpleDateFormat Md = new SimpleDateFormat("MM-dd");
    private static SimpleDateFormat MMdd = new SimpleDateFormat("MM/dd");

    private static SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。

    private static SimpleDateFormat ms = new SimpleDateFormat("mm:ss");//初始化Formatter的转换格式。

    private static SimpleDateFormat WEEK = new SimpleDateFormat("EEEE");//初始化Formatter的转换格式。


    public static String getMinuteTime(long time) {
        if (time <= 0)
            return "";
        return ms.format(time);
    }

    public static String getYMDTime(long time) {
        if (time <= 0)
            return "";

        return yMd.format(time);
    }

    public static String getTimeForTask(long time) {
        try {
            return hms.format(time);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return "";
    }

    //朋友圈红包详情的时间转换
    public static String showFcRedDetailTime(long stamp) {
        return hourMinuteFormat.format(stamp * 1000);

    }

    public static String timeFormat(long stamp) {
        return yMdHms.format(stamp);
    }

    public static String timeFormatYMDHM(long stamp) {
        return yMdHm.format(stamp);
    }

    public static String timeFormatYMD(long stamp) {
        return yMd.format(stamp);
    }

    public static String getPlaybookShareInfo(long stamp) {
        return MMdd.format(stamp) + " " + WEEK.format(stamp) + " " + hourMinuteFormat.format(stamp);
    }

    public static String showTimeFormatMMDD(long stamp) {
        String time = "";
        //当前时间
        long currentTime = System.currentTimeMillis();
        //应该分别转换格式对比年月日时分秒
        String currentFormat = yMdHm.format(currentTime);
        String stampFormat = yMdHm.format(stamp);
        //非当年，显示为年月日
        if (!currentFormat.substring(0, 4).equals(stampFormat.substring(0, 4))) {
            time = yMd.format(stamp);
        } else {
            //不是一个月，显示月日
            time = Md.format(stamp);
        }

        return time;
    }

    /**
     * 聊天界面里用到的时间
     *
     * @param stamp
     * @return
     */
    public static String showFormatTimeForChat(long stamp) {
        SimpleDateFormat c_yMdHm = new SimpleDateFormat("yyyy\nMM/dd\nHH:mm");
        SimpleDateFormat c_MdHm = new SimpleDateFormat("MM/dd\nHH:mm");
        long currentTime = System.currentTimeMillis();
        String currentFormat = yMdHm.format(currentTime);
        String stampFormat = yMdHm.format(stamp);
        if (!currentFormat.substring(0, 4).equals(stampFormat.substring(0, 4))) {
            return c_yMdHm.format(stamp);
        }

        if (stampFormat.substring(5, 16).equals(currentFormat.substring(5, 16))) {
            return "刚刚";
        }
        return c_MdHm.format(stamp);
    }

    public static String getHourMinutesTime() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = hourMinuteFormat.format(curDate);
        return str;
    }


    /**
     * 将当前的月份和日期转换成英文+数字的形式
     *
     * @return
     */
    public static String date2en() {
        long currentTime = System.currentTimeMillis();
        String currentFormat = Md.format(currentTime);
        String month = month2en(currentFormat.substring(0, 2));
        return month + " " + currentFormat.substring(3);
    }

    /**
     * 获取当前时间是上午还是下午
     *
     * @return
     */
    public static String getNoon() {
        long time = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int apm = mCalendar.get(Calendar.AM_PM);
        if (apm == 0) {
            return "AM";
        } else {
            return "PM";
        }
    }

    /**
     * 月份字符串转换成英文
     *
     * @param month
     * @return
     */
    public static String month2en(String month) {
        String en = "";
        int monthI = Integer.valueOf(month);
        if (monthI > 12) {
            en = "";
        } else {
            if (monthI == 1) {
                en = "January";
            } else if (monthI == 2) {
                en = "February";
            } else if (monthI == 3) {
                en = "March";
            } else if (monthI == 4) {
                en = " April";
            } else if (monthI == 5) {
                en = "May";
            } else if (monthI == 6) {
                en = "June";
            } else if (monthI == 7) {
                en = "July";
            } else if (monthI == 8) {
                en = "August";
            } else if (monthI == 9) {
                en = " September";
            } else if (monthI == 10) {
                en = "October";
            } else if (monthI == 11) {
                en = "November";
            } else if (monthI == 12) {
                en = "December";
            }
        }
        en = en.substring(0, 3);
        return en;
    }

    /**
     * 获取当年年份
     *
     * @return
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getDay() {
        long currentTime = System.currentTimeMillis();
        String currentFormat = Md.format(currentTime);
        return currentFormat.substring(3);
    }

    /**
     * 得到现在小时
     *
     * @return
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 判断是否为同一天
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isSameDay(long time1, long time2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time1));
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(new Date(time2));
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        return day1 == day2;
    }

    public static String stringForTime(long timeMs) {
        long totalSeconds = timeMs / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
}