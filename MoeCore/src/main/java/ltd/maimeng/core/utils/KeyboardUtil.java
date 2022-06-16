package ltd.maimeng.core.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/08/21
 *     desc   :
 * </pre>
 */
public class KeyboardUtil {

    // activity的根视图
    private View rootView;
    // 记录根视图的显示高度
    private int rootViewVisibleHeight;
    // 系统键盘弹起收起监听
    private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;

    public KeyboardUtil(Activity activity) {
        // 获取activity的根视图
        rootView = activity.getWindow().getDecorView();
        // 监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 获取当前根视图在屏幕上显示的大小
                Rect r = new Rect();
                // 获取rootView在窗体的可视区域
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();
                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
                // 根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }
                // 根视图显示高度变小超过200，可以看作软键盘显示了
                if (rootViewVisibleHeight - visibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyboardOpen(rootViewVisibleHeight - visibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
                // 根视图显示高度变大超过200，可以看作软键盘隐藏了
                if (visibleHeight - rootViewVisibleHeight > 200) {
                    if (onSoftKeyBoardChangeListener != null) {
                        onSoftKeyBoardChangeListener.keyboardHide(visibleHeight - rootViewVisibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
            }
        });
    }

    private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
    }

    public interface OnSoftKeyBoardChangeListener {

        void keyboardOpen(int height);

        void keyboardHide(int height);
    }

    public static void setListener(Activity activity, OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        KeyboardUtil softKeyBoardListener = new KeyboardUtil(activity);
        softKeyBoardListener.setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
    }

    public static boolean isOpen(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }


    /**
     * @MethodName:openInputMethod
     * @Description:打开系统软键盘
     */
    public static void open(final EditText editText) {
        Handler mDelivery = new Handler(Looper.getMainLooper());
        mDelivery.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                // 请求获得焦点
                editText.requestFocus();
                // 调用系统输入法
                InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }, 200);
    }

    /**
     * @MethodName:closeInputMethod
     * @Description:关闭系统软键盘
     */
    public static void hide(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
        }
    }
}
