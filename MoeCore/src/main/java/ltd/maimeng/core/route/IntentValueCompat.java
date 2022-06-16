package ltd.maimeng.core.route;

import android.content.Intent;

import ltd.maimeng.core.log.TeaLog;
import ltd.maimeng.core.utils.TextUtil;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * 用来处理intent中的参数转换问题，例如在intent中
 * put了一个long类型的参数，用String去取就会出问题。
 */
public class IntentValueCompat {

    public static final String TAG = "IntentValueCompat";

    /**
     * 从String/int/long/double中获取String参数
     *
     * @param key
     * @param intent
     * @return
     */
    public static String getString(@NonNull String key, @NonNull Intent intent) {
        String SResult = intent.getStringExtra(key);
        if (!TextUtil.isEmpty(SResult)) {
            return SResult;
        }

        int IResult = intent.getIntExtra(key, Integer.MIN_VALUE);
        if (IResult != Integer.MIN_VALUE) {
            return IResult + "";
        }

        long LResult = intent.getLongExtra(key, Long.MIN_VALUE);
        if (LResult != Long.MIN_VALUE) {
            return LResult + "";
        }

        double DResult = intent.getDoubleExtra(key, Double.MIN_VALUE);
        if (DResult != Double.MIN_VALUE) {
            return DResult + "";
        }

        return null;
    }

    /**
     * 从String/int/long/double中获取int参数
     *
     * @param key
     * @param intent
     * @return
     */
    public static int getInt(@NonNull String key, @NonNull Intent intent) {
        return getInt(key, intent, Integer.MIN_VALUE);
    }

    /**
     * 从String/int/long/double中获取int参数
     *
     * @param key
     * @param intent
     * @return
     */
    public static int getInt(@NonNull String key, @NonNull Intent intent, int defaultValue) {
        String SResult = intent.getStringExtra(key);
        if (!TextUtil.isEmpty(SResult)) {
            if (NumberUtils.isDigits(SResult)) {
                // 首先所有的字符必须是数字，小数都不行，必须纯数字
                BigInteger bigInteger = NumberUtils.createBigInteger(SResult);
                return bigInteger.intValue();
            } else {
                return defaultValue;
            }
        }

        int IResult = intent.getIntExtra(key, defaultValue);
        if (IResult != defaultValue) {
            return IResult;
        }

        long LResult = intent.getLongExtra(key, defaultValue);
        if (LResult != defaultValue) {
            if (LResult >= Integer.MIN_VALUE && LResult <= Integer.MAX_VALUE) {
                return (int) LResult;
            } else {
                return defaultValue;
            }
        }

        double DResult = intent.getDoubleExtra(key, defaultValue);
        if (DResult != defaultValue) {
            if (DResult >= Integer.MIN_VALUE && DResult <= Integer.MAX_VALUE) {
                return (int) LResult;
            } else {
                return defaultValue;
            }
        }

        return defaultValue;
    }

    /**
     * 从String/int/long/double中获取long参数
     *
     * @param key
     * @param intent
     * @return
     */
    public static long getLong(@NonNull String key, @NonNull Intent intent) {
        return getLong(key, intent, Integer.MIN_VALUE);
    }

    /**
     * 从String/int/long/double中获取long参数
     *
     * @param key
     * @param intent
     * @return
     */
    public static long getLong(@NonNull String key, @NonNull Intent intent, long defaultValue) {
        TeaLog.i(TAG, "defaultValue = " + defaultValue);
        String SResult = intent.getStringExtra(key);
        TeaLog.i(TAG, "SResult = " + SResult);
        if (!TextUtil.isEmpty(SResult)) {
            if (NumberUtils.isDigits(SResult)) {
                //首先所有的字符必须是数字，小数都不行，必须纯数字
                BigInteger bigInteger = NumberUtils.createBigInteger(SResult);
                return bigInteger.longValue();
            } else {
                return defaultValue;
            }
        }

        int IResult = intent.getIntExtra(key, Integer.MIN_VALUE);
        TeaLog.i(TAG, "IResult = " + IResult);
        if (IResult != Integer.MIN_VALUE) {
            return IResult;
        }

        long LResult = intent.getLongExtra(key, Long.MIN_VALUE);
        TeaLog.i(TAG, "LResult = " + LResult);
        if (LResult != Long.MIN_VALUE) {
            return LResult;
        }

        double DResult = intent.getDoubleExtra(key, Double.MIN_VALUE);
        TeaLog.i(TAG, "DResult = " + DResult);
        if (DResult != Double.MIN_VALUE) {
            return (long) DResult;
        }

        TeaLog.i(TAG, "return defaultValue = " + defaultValue);

        return defaultValue;
    }

    /**
     * 从String/boolean中获取boolean参数
     *
     * @param key
     * @param intent
     * @return
     */
    public static boolean getBoolean(@NonNull String key, @NonNull Intent intent, boolean defaultValue) {
        String SResult = intent.getStringExtra(key);
        if (!TextUtil.isEmpty(SResult)) {
            if (SResult.toLowerCase().equals("true")) {
                return true;
            } else if (SResult.toLowerCase().equals("false")) {
                return false;
            }
        }
        return intent.getBooleanExtra(key, defaultValue);
    }

    /**
     * 从String/int/long/double中获取double参数
     *
     * @param key
     * @param intent
     * @return
     */
    public static double getDouble(@NonNull String key, @NonNull Intent intent) {
        String SResult = intent.getStringExtra(key);
        if (!TextUtil.isEmpty(SResult)) {
            //如果是数字类型
            if (NumberUtils.isNumber(SResult)) {
                return NumberUtils.toDouble(SResult);
            }
        }

        int IResult = intent.getIntExtra(key, Integer.MIN_VALUE);
        if (IResult != Integer.MIN_VALUE) {
            return IResult;
        }

        long LResult = intent.getLongExtra(key, Long.MIN_VALUE);
        if (LResult != Long.MIN_VALUE) {
            return LResult;
        }

        double DResult = intent.getDoubleExtra(key, Double.MIN_VALUE);
        if (DResult != Double.MIN_VALUE) {
            return DResult;
        }

        return Double.MIN_VALUE;
    }

    /**
     * 从intent中获取ArrayList<Serializable>参数
     *
     * @param key
     * @param intent
     * @return
     */
    public static <T extends Serializable> ArrayList<T> getSerializableArrayList(@NonNull String key, @NonNull Intent intent) {
        Serializable serializable = intent.getSerializableExtra(key);
        if (serializable != null && serializable instanceof ArrayList) {
            return (ArrayList<T>) serializable;
        }
        return new ArrayList<T>();
    }
}
