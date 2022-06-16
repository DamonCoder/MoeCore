package ltd.maimeng.core.ui.dialog.builder;

import android.content.Context;

import ltd.maimeng.core.ui.dialog.custom.DialogPlusBuilder;
import ltd.maimeng.core.ui.dialog.custom.ListHolder;


/**
 * 自定义Dialog控制类:ListDialog
 */
public class MoeCustomListDialogBuilder extends DialogPlusBuilder {

    public MoeCustomListDialogBuilder(Context context) {
        super(context);
        setContentHolder(new ListHolder());
    }

}
