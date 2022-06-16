package ltd.maimeng.core.ui.list;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/14
 *     desc   :
 * </pre>
 */
public abstract class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private int layoutResID = View.NO_ID;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        findViews();
    }

    public void setLayoutResID(int layoutResID) {
        this.layoutResID = layoutResID;
    }

    public int getLayoutResID() {
        return layoutResID;
    }

    public abstract void findViews();

    public abstract void setData(Object object);

}
