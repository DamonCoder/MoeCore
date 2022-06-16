package ltd.maimeng.core.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/11/02
 *     desc   :
 * </pre>
 */
public class SetableScrollView extends ScrollView {

    private boolean scroll = true;

    private int downX, downY;
    private int mTouchSlop;

    public SetableScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public SetableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public SetableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public SetableScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setScroll(boolean scroll) {
        this.scroll = scroll;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scroll) {
            return super.onTouchEvent(ev);
        } else {
            return true;
        }
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                downX = (int) ev.getRawX();
//                downY = (int) ev.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int moveY = (int) ev.getRawY();
//                // 判断是否滑动，若滑动就拦截事件
//                if (Math.abs(moveY - downY) > mTouchSlop) {
//                    return true;
//                }
//                break;
//            default:
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
}
