package ltd.maimeng.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangw on 2018/4/3.
 */
public abstract class MoeApplication extends Application {

    public static MoeApplication mInstance;
    protected static Context appContext;
    // 线程池
    protected static ExecutorService threadPoolExecutor;
    private WeakReference<Activity> sCurrentActivityWeakRef;
    private int mActivityCount = 0;

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        appContext = getApplicationContext();

        // 开启MoeSDK
        Moe.getInstance().start(MoeApplication.this);

        threadPoolExecutor = Executors.newCachedThreadPool(new IFThreadFactory());
//        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//
//            }
//
//            @Override
//            public void onActivityStarted(Activity activity) {
//                mActivityCount++;
//                getAppStatus();
//            }
//
//            @Override
//            public void onActivityResumed(Activity activity) {
//                sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
//            }
//
//            @Override
//            public void onActivityPaused(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityStopped(Activity activity) {
//                mActivityCount--;
//                if (mActivityCount == 0) {
//                    // 应用退入后台
//                    getAppStatus();
//                }
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//            }
//
//            @Override
//            public void onActivityDestroyed(Activity activity) {
//
//            }
//        });
    }

//    /**
//     * 根据activityCount,判断app状态
//     */
//    public void getAppStatus() {
//        if (mActivityCount == 0) {
//            // App进入后台或者APP锁屏了
//            EventBus.getDefault().post(new APPToBackgroundEvent(APPToBackgroundEvent.TYPE_BACKGROUND));
//        } else {
//            // App进入前台
//            EventBus.getDefault().post(new APPToBackgroundEvent(APPToBackgroundEvent.TYPE_FOREGROUND));
//        }
//    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public int getActivityCount() {
        return mActivityCount;
    }

    public static MoeApplication getInstance() {
        return mInstance;
    }

    /**
     * 耗时任务可以放在这里执行,操作完成后,利用UIHandler更新UI
     *
     * @param runnable
     */
    public static void poolExecute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    /**
     * The default thread factory.
     */
    private static class IFThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        IFThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "iF_pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }


        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement() + r.getClass().getSimpleName(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // synSystemCurrentLocale();
        // LocaleManageUtil.setLocal(getApplicationContext());
        // LocaleManageUtil.setApplicationLanguage(getApplicationContext());
    }
}