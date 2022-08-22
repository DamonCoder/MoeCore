package ltd.maimeng.sdk.framework;

import ltd.maimeng.core.Moe;
import ltd.maimeng.core.MoeApplication;
import ltd.maimeng.sdk.BuildConfig;

public class AppApplication extends MoeApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Moe.getInstance().printLog(BuildConfig.LOG_ENABLE);
    }
}
