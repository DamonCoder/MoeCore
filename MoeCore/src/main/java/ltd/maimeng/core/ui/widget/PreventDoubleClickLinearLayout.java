package ltd.maimeng.core.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2021/01/22
 *     desc   :
 * </pre>
 */
public class PreventDoubleClickLinearLayout extends LinearLayout {

    private long lastClickTime;
    private final long SPACE_TIME = 300;

    public PreventDoubleClickLinearLayout(Context context) {
        super(context);
    }

    public PreventDoubleClickLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PreventDoubleClickLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PreventDoubleClickLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > SPACE_TIME) {
            super.setOnClickListener(l);
        }
        lastClickTime = currentTime;
    }
}
