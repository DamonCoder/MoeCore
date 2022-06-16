package ltd.maimeng.core.network;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Looper;

import androidx.fragment.app.Fragment;

public class MoeBaseRequest {

    /**
     * 检测Activity是否可用，只支持4.2及以上。
     * 如果低于4.2则默认Activity可用
     *
     * @param activity
     * @return
     */
    public boolean isActivityOk(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !activity.isDestroyed();
        } else {
            return true;
        }
    }

    /**
     * 检测Fragment是否可用
     *
     * @param fragment
     * @return
     */
    public boolean isFragmentOk(Fragment fragment) {

        if (fragment == null) {
            return false;
        }

        if (fragment.getActivity() == null) {
            return false;
        }

        if (fragment.isDetached() || fragment.isRemoving()) {
            return false;
        }

        return true;
    }

    /**
     * Context是否可用
     *
     * @param context
     * @return
     */
    public boolean isContextOk(Context context) {
        if (context == null) {
            return false;
        }
        return true;
    }

    /**
     * 是否在主线程运行
     */
    public boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 是否在子线程运行
     */
    public boolean isOnBackgroundThread() {
        return !isOnMainThread();
    }
}
