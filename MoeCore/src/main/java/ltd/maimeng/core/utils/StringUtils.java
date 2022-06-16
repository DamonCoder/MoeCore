package ltd.maimeng.core.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangw on 2018/4/11.
 */

public class StringUtils {

    public static String generateCurrentSeconds() {
        String timeStr = String.valueOf(System.currentTimeMillis());
        return timeStr.substring(0, 10);
    }

    public static String generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return new String(digits);
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 生成字符串长度
     * @return 随机字符串
     */
    public static synchronized String createRandomString(int length) {

        char ch[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
                'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1'};

        Random random = new Random();

        if (length > 0) {
            int index = 0;
            char[] temp = new char[length];
            int num = random.nextInt();
            for (int i = 0; i < length % 5; i++) {
                temp[index++] = ch[num & 63];
                num >>= 6;
            }
            for (int i = 0; i < length / 5; i++) {
                num = random.nextInt();
                for (int j = 0; j < 5; j++) {
                    temp[index++] = ch[num & 63];
                    num >>= 6;
                }
            }
            return new String(temp, 0, length);
        } else if (length == 0) {
            return "";
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str) || "".equals(str.trim()) || "null".equals(str);
    }

//    public static void setLink(String str, TextView view) {
//        setLink(str, view, null);
//    }

//    /**
//     * 将TextView中的url变成可点击的
//     *
//     * @param str
//     * @param view
//     * @param callback 拦截点击url回调事件
//     */
//    public static void setLink(String str, TextView view, ClickableSpanEx.ClickableSpanCallback callback) {
//        if (TextUtils.isEmpty(str))
//            return;
//        SpannableString tmpSp = new SpannableString(str);
//        Linkify.addLinks(tmpSp, Linkify.ALL);
//        URLSpan[] spans = tmpSp.getSpans(0, str.length(), URLSpan.class);
//        SpannableString sp = callback != null ? new SpannableString(str) : tmpSp;
//        if (spans.length > 0) {
//            for (URLSpan span : spans) {
//                if (callback != null) {
//                    String url = span.getURL();
//                    if (url.indexOf("http://") == 0 || url.indexOf("https://") == 0 || url.indexOf("www.") == 0) {
//                        sp.setSpan(new ClickableSpanEx(url, callback), tmpSp.getSpanStart(span), tmpSp.getSpanEnd(span), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//                    } else {
//                        sp.setSpan(span, tmpSp.getSpanStart(span), tmpSp.getSpanEnd(span), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//                    }
//                }
//                sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0D6CE2")), tmpSp.getSpanStart(span), tmpSp.getSpanEnd(span), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//            view.setText(sp);
////            view.setMovementMethod(LinkMovementMethod.getInstance());
//            view.setMovementMethod(LinkMovementClickMethod.getInstance());
//        } else {
//            view.setText(sp);
//        }
//    }

    public static String numConvertToStr(long num) {
        if (num < 10000)
            return String.valueOf(num);
        else if (num < 10000 * 10000) {
            long w = num / 10000;
            long q = num % 10000 / 1000;
            if (q > 0)
                return w + "." + q + "万";
            else
                return w + "万";

        } else {
            return (num / (10000 * 10000)) + "亿";
        }
    }

    public static String numStrFromat(String num) {
        if (TextUtils.isEmpty(num))
            return num;
        int index = num.indexOf(".");
        if (index < 0)
            return num;
        return num.substring(0, Math.min(num.length(), index + 3));
    }

    public static String doubleFromat2(double num) {
        return new DecimalFormat("##0.##").format(num);
    }

    public static String doubleFromat(double num) {
        return new DecimalFormat("##0.######").format(num);
    }

    public static String doubleFromat8(double num) {
        return new DecimalFormat("##0.########").format(num);
    }

    public static double stringToDouble(String str) {
        double num = 0;
        try {
            BigDecimal maxlng1 = new BigDecimal(str);
            num = maxlng1.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return num;
    }

    /**
     * {w, h}转换成int数组
     *
     * @param str 格式：{w, h}
     * @return [width, height]
     */
    public static int[] sizeStrTointArr(String str) {
        try {
            String[] ss = str.replace("{", "").replace("}", "").split(", ");
            int w = Integer.valueOf(ss[0]);
            int h = 0;
            if (ss.length > 1)
                h = Integer.valueOf(ss[1]);
            return new int[]{w, h};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new int[]{0, 0};
    }

    /**
     * 宽高转换成字符串
     *
     * @param w
     * @param h
     * @return
     */
    public static String widthHeightToStr(int w, int h) {
        return "{" + w + ", " + h + "}";
    }

    /**
     * 验证字符串是否只包含中英文 数字
     *
     * @param str
     * @return
     */
    public static boolean isCN_EN_Num(String str) {
        // 只允许字母、数字和汉字
        String regEx = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    public static boolean isCN_EN_Num(CharSequence charSequence) {
        // 只允许字母、数字和汉字
        String regEx = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(charSequence);
        return m.matches();
    }

    /**
     * 验证字符串是否只包含中英文
     *
     * @param charSequence
     * @return
     */
    public static boolean isCN_EN(CharSequence charSequence) {
        // 只允许字母、汉字
        String regEx = "^[a-zA-Z\u4E00-\u9FA5]+$";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(charSequence);
        return m.matches();
    }

    /**
     * MD5 加密
     *
     * @param str
     * @return
     */
    public static String getMD5Str(String str) {
        if (!StringUtils.isEmpty(str)) {
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.reset();
                messageDigest.update(str.getBytes("UTF-8"));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }

            final byte[] byteArray = messageDigest.digest();

            final StringBuffer md5StrBuff = new StringBuffer();

            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(
                            Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
            // 16位加密，从第9位到25位
            return md5StrBuff.substring(8, 24).toString().toUpperCase();
        }
        return "";
    }

}
