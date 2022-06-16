package ltd.maimeng.core.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

public class NetworkUtil {

    /**
     * 判断网络是否连接
     *
     * @param context 上下文
     * @return true网络连接，false网络未连接
     */
    public static boolean isNetConnected(@NonNull Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

//    /**
//     * 判断当前网络状态是否可用
//     *
//     * @return
//     */
//    public static boolean isNetConnected(Context context) {
//        try {
//            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            if (connectivity != null) {
//                NetworkInfo info = connectivity.getActiveNetworkInfo();
//                if (info != null) {
//                    return info.isConnectedOrConnecting();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    /**
     * 判断网络是否为WIFI连接状态
     *
     * @param context 上下文
     * @return trueWIFI连接状态，false非WIFI连接状态
     */
    public static boolean isWifiConnected(@NonNull Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            return manager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }

//    /**
//     * 判断当前网络是否是wifi类型的网络
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isWIFINetWork(Context context) {
//        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity == null) {
//            return false;
//        } else {
//            NetworkInfo net = connectivity.getActiveNetworkInfo();
//            if (net != null && net.getState() != null && net.getState() == NetworkInfo.State.CONNECTED) {
//                int type = net.getType();
//                return type == ConnectivityManager.TYPE_WIFI;
//            }
//        }
//        return false;
//    }

    /**
     * 判断当前网络是否是2G类型的网络
     *
     * @param context 上下文
     * @return true2G连接状态，false非2G连接状态
     */
    public static boolean is2GConnected(@NonNull Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS || info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA
                        || info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
                    return true;
                }
            }
        }
        return false;
    }

//    /**
//     * convert URL to URI to deal with URISyntaxException
//     *
//     * @param url url
//     * @return URI
//     */
//    public static URI url2uri(URL url) {
//        String query = url.getQuery();
//        if (query != null)
//            query = URLDecoder.decode(query);
//        try {
//            return new URI(url.getProtocol(), url.getHost(), url.getPath(), query, null);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * 打开网络设置界面
     *
     * @param activity 当前activity
     */
    public static void openNetSetting(@NonNull Activity activity) {
        Intent intent = new Intent("/");
        ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 获取网络连接类型
     *   0:unknown   1:wifi   2:2g   3:3g   4:4g   5:ethernet
     *
     * @param context
     * @return
     */
    public static int getConnectionType(@NonNull Context context) {
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return 0;
        } else {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return 1;
                }
                // 下面的判断代码来自TelephonyManager中隐藏的方法getNetworkClass
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return 2;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                        return 3;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    // case TelephonyManager.NETWORK_TYPE_LTE_CA:
                        return 4;
                    default:
                        return 0;
                }
            }
        }
        return 0;
    }
}
