package ltd.maimeng.core.ui.dialog.builder;

import android.content.Context;

import ltd.maimeng.core.ui.dialog.custom.DialogPlusBuilder;
import ltd.maimeng.core.ui.dialog.custom.GridHolder;


/**
 * 自定义Dialog控制类:GridDialog
 */
public class MoeCustomGridDialogBuilder extends DialogPlusBuilder {

    public MoeCustomGridDialogBuilder(Context context, int column) {
        super(context);
        setContentHolder(new GridHolder(column));
    }
}
