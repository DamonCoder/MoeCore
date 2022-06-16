package ltd.maimeng.core.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import ltd.maimeng.core.R;

import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/16
 *     desc   :
 * </pre>
 */
public class StatusBarView extends View {

    private static int mStatusBarHeight;

    public StatusBarView(Context context) {
        this(context, null);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBarHeight = getStatusBarHeight(context);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setBackgroundColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBackgroundResource(R.drawable.shape_statusbar_bg);
        } else {
            setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mStatusBarHeight);
    }

    // 此处代码可以放到StatusBarUtils
    public static int getStatusBarHeight(Context context) {
        if (mStatusBarHeight == 0) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                mStatusBarHeight = res.getDimensionPixelSize(resourceId);
            }
        }
        return mStatusBarHeight;
    }
}
