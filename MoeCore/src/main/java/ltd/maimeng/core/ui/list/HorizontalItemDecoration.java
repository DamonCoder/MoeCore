package ltd.maimeng.core.ui.list;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2021/05/06
 *     desc   :
 * </pre>
 */
public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {

    private int spaceH;
    private int spaceV;

    public HorizontalItemDecoration(int spaceH, int spaceV) {
        this.spaceH = spaceH;
        this.spaceV = spaceV;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = spaceH;
        outRect.top = spaceV;
        outRect.bottom = spaceV;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0) {
            outRect.left = spaceH;
        }
    }
}