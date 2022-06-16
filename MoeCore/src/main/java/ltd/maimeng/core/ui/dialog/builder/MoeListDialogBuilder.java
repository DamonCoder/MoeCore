package ltd.maimeng.core.ui.dialog.builder;

import android.content.Context;
import android.content.DialogInterface;

/**
 * 列表菜单创建类
 */
public class MoeListDialogBuilder extends MoeBaseDialogBuilder<MoeListDialogBuilder> {

    public MoeListDialogBuilder(Context context){
        super(context);
    }

    public MoeListDialogBuilder setItems(String[] items, DialogInterface.OnClickListener listener){
        dialogBuilder.setItems(items,listener);
        return this ;
    }


}
