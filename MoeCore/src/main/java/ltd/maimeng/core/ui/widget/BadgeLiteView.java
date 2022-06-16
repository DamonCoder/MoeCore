package ltd.maimeng.core.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/11/26
 *     desc   :
 * </pre>
 */
public class BadgeLiteView extends TextView {

    public BadgeLiteView(Context context) {
        super(context);
    }

    public BadgeLiteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BadgeLiteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public BadgeLiteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    public void setBadgeNum(int badgeNum) {
        if (badgeNum == 0) {
            this.setVisibility(View.GONE);
        } else {
            this.setVisibility(View.VISIBLE);
            if (badgeNum == -1) {
                this.setTextSize(5);
                this.setText("");
            } else if (badgeNum > 99) {
                this.setTextSize(10);
                this.setText("99+");
            } else {
                this.setTextSize(10);
                this.setText(String.valueOf(badgeNum));
            }
        }
    }
}
