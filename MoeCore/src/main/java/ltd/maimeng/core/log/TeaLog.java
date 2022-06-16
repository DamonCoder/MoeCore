package ltd.maimeng.core.log;

import android.util.Log;

import com.orhanobut.logger.Logger;
import ltd.maimeng.core.Moe;


public class TeaLog {

    public static final String TAG = "Moe";

    // Logger.d("hello");
    // Logger.e("hello");
    // Logger.w("hello");
    // Logger.v("hello");
    // Logger.wtf("hello");
    // Logger.json(JSON_CONTENT);
    // Logger.xml(XML_CONTENT);

    public static void i(String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            String logMessage = getLogMessage();
            Log.i(TAG, logMessage + " -> " + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            String logMessage = getLogMessage();
            // Log.i(tag, logMessage + " -> " + msg);
            if (msg.length() > 3072) {
                for (int i = 0; i < msg.length(); i += 3072) {
                    if (i + 3072 < msg.length()) {
                        Log.i(tag, logMessage + " -> " + msg.substring(i, i + 3072));
                    } else {
                        Log.i(tag, logMessage + " -> " + msg.substring(i, msg.length()));
                    }
                }
            } else {
                Log.i(tag, logMessage + " -> " + msg);
            }
        }
    }

    public static void d(String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            String logMessage = getLogMessage();
            Log.d(TAG, logMessage + " -> " + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            String logMessage = getLogMessage();
            Log.d(tag, logMessage + " -> " + msg);
        }
    }

    public static void v(String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            Log.v(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            Log.v(tag, msg);
        }
    }

    public static void e(String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            String logMessage = getLogMessage();
            Log.e(TAG, logMessage + " -> " + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            String logMessage = getLogMessage();
            Log.e(tag, logMessage + " -> " + msg);
        }
    }

    public static void eByLogger(String msg) {
        Logger.e(msg);
    }

    public static void iByLogger(String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            Logger.i(msg);
        }
    }

    public static void vByLogger(String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            Logger.v(msg);
        }
    }

    public static void dByLogger(Object msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            Logger.d(msg);
        }
    }

    public static void wByLogger(String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            Logger.w(msg);
        }
    }

    public static void xmlByLogger(String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            Logger.xml(msg);
        }
    }

    public static void jsonByLogger(String msg) {
        if (Moe.getInstance().isPrintLogEnable()) {
            Logger.json(msg);
        }
    }

    public static String getLogMessage() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (null == stackTraceElements) {
            return null;
        }
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.isNativeMethod()) {
                continue;
            }
            if (stackTraceElement.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (stackTraceElement.getClassName().equals(TeaLog.class.getName())) {
                continue;
            }
            return "[" + Thread.currentThread().getName() + ":("
                    + stackTraceElement.getFileName() + ":"
                    + stackTraceElement.getLineNumber() + "):"
                    + stackTraceElement.getMethodName() + "]";
        }
        return null;
    }
}
