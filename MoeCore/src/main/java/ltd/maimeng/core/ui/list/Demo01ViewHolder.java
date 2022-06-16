package ltd.maimeng.core.ui.list;

import android.util.Log;
import android.view.View;

import ltd.maimeng.core.R;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/14
 *     desc   :
 * </pre>
 */
public class Demo01ViewHolder extends RecyclerViewHolder {

    public static int LAYOUT_ID = R.layout.demo_holder_01;

    public Demo01ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void findViews() {
        Log.i("Coder", "Demo01ViewHolder.findViews");
    }

    @Override
    public void setData(Object object) {
        Log.i("Coder", "Demo01ViewHolder.setData");
    }
}
