package ltd.maimeng.core.ui.dialog;

import android.content.Context;

import ltd.maimeng.core.ui.dialog.builder.MoeCustomGridDialogBuilder;
import ltd.maimeng.core.ui.dialog.builder.MoeCustomListDialogBuilder;
import ltd.maimeng.core.ui.dialog.builder.MoeDialogBuilder;
import ltd.maimeng.core.ui.dialog.builder.MoeListDialogBuilder;


/**
 * Dialog快速构建器
 */
public class MoeDialog {

    public static MoeListDialogBuilder createList(Context context) {
        return new MoeListDialogBuilder(context);
    }

    public static MoeDialogBuilder createOrigin(Context context) {
        return new MoeDialogBuilder(context);
    }

    /**
     * 嵌入型列表Dialog，该Dialog会附着在Activity的decoderView上
     * 基于DialogPlus源码二次开发，
     * 使用方式可参考https://github.com/orhanobut/dialogplus
     *
     * @param context
     * @return
     */
    public static MoeCustomListDialogBuilder createInsertionList(Context context) {
        return new MoeCustomListDialogBuilder(context);
    }

    /**
     * 嵌入型宫格Dialog，该Dialog会附着在Activity的decoderView上
     * 基于DialogPlus源码二次开发，
     * 使用方式可参考https://github.com/orhanobut/dialogplus
     *
     * @param context
     * @param column  列数
     * @return
     */
    public static MoeCustomGridDialogBuilder createInsertionGrid(Context context, int column) {
        return new MoeCustomGridDialogBuilder(context, column);
    }

}
