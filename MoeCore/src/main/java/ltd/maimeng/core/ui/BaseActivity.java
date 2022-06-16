package ltd.maimeng.core.ui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import ltd.maimeng.core.network.lifecycle.Lifecycle;
import ltd.maimeng.core.network.lifecycle.LifecycleManager;
import ltd.maimeng.core.network.status.NetStateChangeObserver;
import ltd.maimeng.core.network.status.NetStateChangeReceiver;
import ltd.maimeng.core.network.status.NetworkType;
import ltd.maimeng.core.ui.status.StatusActivity;
import com.hjq.toast.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public abstract class BaseActivity extends StatusActivity implements NetStateChangeObserver {

    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private LifecycleManager lifecycleManager = new LifecycleManager();
    // private ActivityCallbackManager callbackManager = new ActivityCallbackManager();
    protected boolean netDisconnected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetStateChangeReceiver.registerReceiver(this);
    }

    @Override
    public ComponentName startService(Intent service) {
        // return super.startService(service);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return super.startForegroundService(service);
        } else {
            return super.startService(service);
        }
    }

    @Override
    public void sendBroadcast(Intent intent) {
        // super.sendBroadcast(intent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    protected void registBroadcastReceiver(String... actions) {
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    onReceiveBroadcast(intent);
                }
            };
        }
        if (intentFilter == null) {
            intentFilter = new IntentFilter();
        }
        for (String action : actions) {
            intentFilter.addAction(action);
        }
        LocalBroadcastManager.getInstance(BaseActivity.this).registerReceiver(broadcastReceiver, intentFilter);
    }

    protected void onReceiveBroadcast(Intent intent) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleManager.onResume();
        NetStateChangeReceiver.registerObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycleManager.onPause();
        NetStateChangeReceiver.unRegisterObserver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifecycleManager.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(BaseActivity.this).unregisterReceiver(broadcastReceiver);
        }
        NetStateChangeReceiver.unRegisterReceiver(this);

        // overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        lifecycleManager.onDestroy();
        // callbackManager.release();
    }

    // @Override
    // public void onBackPressed() {
    //     super.onBackPressed();
    //     removeActivity();
    // }

    public void addLifecycleListener(@NonNull Lifecycle lifecycle) {
        lifecycleManager.add(lifecycle);
    }

    public void removeLifecycleListener(@NonNull Lifecycle lifecycle) {
        lifecycleManager.remove(lifecycle);
    }

    public void removeLifecycleListener(@NonNull String listenerId) {
        lifecycleManager.remove(listenerId);
    }

//    public void addActivityCallback(ActivityCallback callback) {
//        callbackManager.addCallback(callback);
//    }
//
//    public void removeActivityCallback(ActivityCallback callback) {
//        callbackManager.removeCallback(callback);
//    }
//
//    public void removeActivityCallback(String callbackId) {
//        callbackManager.removeCallback(callbackId);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        callbackManager.onConfigurationChanged(newConfig);
//    }

    @Override
    public void onNetDisconnected() {
        netDisconnected = true;
        ToastUtils.show("网络连接不可用，请稍后重试");
    }

    @Override
    public void onNetConnected(NetworkType networkType) {
        netDisconnected = false;
    }
}
