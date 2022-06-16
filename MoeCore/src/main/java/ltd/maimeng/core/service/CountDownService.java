package ltd.maimeng.core.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import ltd.maimeng.core.utils.AppUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/06/18
 *     desc   : 用于在后台进行倒计时的Service
 * </pre>
 */
public class CountDownService extends Service {

    private String CHANNEL_ID = AppUtil.getPackageName();
    private String CHANNEL_NAME = AppUtil.getAppName();

    /**
     * 控制线程运行的开关
     */
    public static boolean COUNTDOWN = true;

    /**
     * 倒计时线程
     */
    private CountDownThread countDownThread;

    /**
     * key = CountDownBean.action
     * value = CountDownBean
     */
    private static Map<String, CountDownBean> mapCountDown = new ConcurrentHashMap<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager == null)
                return;
            manager.createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setAutoCancel(true)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .setOngoing(true)
                    .setPriority(NotificationManager.IMPORTANCE_LOW)
                    .build();
            startForeground(AppUtil.getVersionCode(), notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (countDownThread == null) {
            clearCountDownBean();
            countDownThread = new CountDownThread();
            countDownThread.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    class CountDownThread extends Thread {
        @Override
        public void run() {
            // super.run();
            while (COUNTDOWN) {
                try {
                    sleep(1000);
                    Set<String> keySet = mapCountDown.keySet();
                    Iterator<String> iterator = keySet.iterator();
                    while (iterator.hasNext()) {
                        String action = iterator.next();
                        CountDownBean bean = mapCountDown.get(action);
                        if (bean != null) {
                            int remain = bean.getMaxCount();
                            decreaseTime(bean);
                            sendTimeData(bean);
                            if (remain <= 1) {
                                iterator.remove();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void decreaseTime(CountDownBean bean) {
        int time = bean.getMaxCount();
        time--;
        bean.setMaxCount(time);
        mapCountDown.put(bean.getAction(), bean);
    }

    private void sendTimeData(CountDownBean bean) {
        Intent intent = new Intent(bean.getAction());
        intent.putExtra(CountDownBean.BEAN, bean);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /**
     * 添加CountDownBean
     *
     * @param bean
     */
    public static void addCountDownBean(CountDownBean bean) {
        mapCountDown.put(bean.getAction(), bean);
    }

    /**
     * 移除CountDownBean
     *
     * @param bean
     */
    public static void removeCountDownBean(CountDownBean bean) {
        if (bean != null) {
            if (mapCountDown.containsKey(bean.getAction())) {
                mapCountDown.remove(bean.getAction());
            }
        }
    }

    public static void clearCountDownBean() {
        if (mapCountDown != null) {
            mapCountDown.clear();
        }
    }
}
