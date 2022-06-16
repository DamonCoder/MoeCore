package ltd.maimeng.core;

import android.app.Application;

import com.hjq.toast.ToastUtils;
import ltd.maimeng.core.network.NetManager;

public class Moe {

    private Application application;
    private boolean printLog = false;

    private static Moe self = null;

    private Moe() {
    }

    public static Moe getInstance() {
        if (self == null) {
            self = new Moe();
        }
        return self;
    }

    public void start(Application application) {
        this.application = application;
        init();
    }

    public void printLog(boolean printLog) {
        this.printLog = printLog;
    }

    public boolean isPrintLogEnable() {
        return printLog;
    }

    private void init() {
        // 初始化网络框架
        NetManager.getInstance().init(application);
        // 初始化Toast框架
        ToastUtils.init(application);
        // TODO
        // printLog(LogUtils.DEBUG);
    }

    public Application getApplication() {
        return application;
    }
}
