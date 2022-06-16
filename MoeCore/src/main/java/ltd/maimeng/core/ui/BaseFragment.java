package ltd.maimeng.core.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.KeyEvent;

import ltd.maimeng.core.network.lifecycle.Lifecycle;
import ltd.maimeng.core.network.lifecycle.LifecycleManager;
import ltd.maimeng.core.ui.status.StatusFragment;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public abstract class BaseFragment extends StatusFragment {

    private LifecycleManager lifecycleManager = new LifecycleManager();
    private BroadcastReceiver baseBroadcastReceiver;
    private IntentFilter intentFilter;

    public void startService(Intent service) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getActivity().startForegroundService(service);
        } else {
            getActivity().startService(service);
        }
    }

    /**
     * 注册广播接收
     *
     * @param actions
     */
    protected void registBroadcastReceiver(String... actions) {
        if (baseBroadcastReceiver == null) {
            baseBroadcastReceiver = new BroadcastReceiver() {
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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(baseBroadcastReceiver, intentFilter);
    }

    // @Override
    public void onReceiveBroadcast(Intent intent) {
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        lifecycleManager.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lifecycleManager.onDestroy();

        if (baseBroadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(baseBroadcastReceiver);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycleManager.onStop();
    }

    public void addLifecycleListener(@NonNull Lifecycle lifecycle) {
        lifecycleManager.add(lifecycle);
    }

    public void removeLifecycleListener(@NonNull Lifecycle lifecycle) {
        lifecycleManager.remove(lifecycle);
    }

    public void removeLifecycleListener(@NonNull String listenerId) {
        lifecycleManager.remove(listenerId);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
