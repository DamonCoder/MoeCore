package ltd.maimeng.core.ui.list;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/11/24
 *     desc   :
 * </pre>
 */
public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

    private int spaceH;
    private int spaceV;

    public VerticalItemDecoration(int spaceH, int spaceV) {
        this.spaceH = spaceH;
        this.spaceV = spaceV;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = spaceH;
        outRect.right = spaceH;
        outRect.bottom = spaceV;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0) {
            outRect.top = spaceV;
        }
    }
}
