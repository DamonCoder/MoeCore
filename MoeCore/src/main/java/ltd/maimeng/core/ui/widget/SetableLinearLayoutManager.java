package ltd.maimeng.core.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/11/02
 *     desc   :
 * </pre>
 */
public class SetableLinearLayoutManager extends LinearLayoutManager {

    private boolean scroll = true;

    public SetableLinearLayoutManager(Context context) {
        super(context);
    }

    public SetableLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public SetableLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScroll(boolean scroll) {
        this.scroll = scroll;
    }

    @Override
    public boolean canScrollVertically() {
        // return super.canScrollVertically();
        if (scroll) {
            return true;
        } else {
            return false;
        }
    }
}
