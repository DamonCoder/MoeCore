package ltd.maimeng.core.ui.dialog.builder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Dialog构造基类
 */
public class MoeBaseDialogBuilder<T> {

    protected AlertDialog.Builder dialogBuilder;
    protected AlertDialog dialog;
    protected boolean canceledOnTouchOutside = true;
    protected int defaultWidthDP = 300;

    public boolean isCanceledOnTouchOutside() {
        return canceledOnTouchOutside;
    }

    public MoeBaseDialogBuilder(Context context) {
        dialogBuilder = new AlertDialog.Builder(context);
    }

    /**
     * 设置icon
     */
    public T setIcon(@DrawableRes int iconId) {
        dialogBuilder.setIcon(iconId);
        return (T) this;
    }

    /**
     * 设置点击空白时是否可关闭对话框
     */
    public T setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        return (T) this;
    }

    /**
     * 设置icon
     */
    public T setIcon(Drawable drawable) {
        dialogBuilder.setIcon(drawable);
        return (T) this;
    }

    /**
     * 设置Title
     */
    public T setTitle(@StringRes int titleId) {
        dialogBuilder.setTitle(titleId);
        return (T) this;
    }

    /**
     * 设置Title
     */
    public T setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            dialogBuilder.setTitle(title);
        }

        return (T) this;
    }

    /**
     * 是否可以取消
     */
    public T setCancelable(boolean cancelable) {
        dialogBuilder.setCancelable(cancelable);
        return (T) this;
    }

    /**
     * 取消按钮/消极按钮
     */
    public T setNegativeButton(@NonNull String negativeText, @NonNull DialogInterface.OnClickListener listener) {
        dialogBuilder.setNegativeButton(negativeText, listener);
        return (T) this;
    }

    /**
     * 取消按钮/消极按钮
     */
    public T setNegativeButton(@StringRes int negativeTextId, @NonNull DialogInterface.OnClickListener listener) {
        dialogBuilder.setNegativeButton(negativeTextId, listener);
        return (T) this;
    }

    /**
     * 确定按钮/积极按钮
     */
    public T setPositiveButton(@NonNull String positiveText, @NonNull DialogInterface.OnClickListener listener) {
        dialogBuilder.setPositiveButton(positiveText, listener);
        return (T) this;
    }

    /**
     * 确定按钮/积极按钮
     */
    public T setPositiveButton(@StringRes int positiveTextId, @NonNull DialogInterface.OnClickListener listener) {
        dialogBuilder.setPositiveButton(positiveTextId, listener);
        return (T) this;
    }


    /**
     * 中性按钮
     */
    public T setNeutralButton(@NonNull String neutralText, @NonNull DialogInterface.OnClickListener listener) {
        dialogBuilder.setNeutralButton(neutralText, listener);
        return (T) this;
    }

    /**
     * 中性按钮
     */
    public T setNeutralButton(@StringRes int neutralTextId, @NonNull DialogInterface.OnClickListener listener) {
        dialogBuilder.setNeutralButton(neutralTextId, listener);
        return (T) this;
    }


    public AlertDialog show() {
        return show(defaultWidthDP);
    }

    /**
     * 指定宽度
     */
    public AlertDialog show(int widthDp) {
        return show(widthDp, canceledOnTouchOutside);
    }

    public AlertDialog show(boolean canceledOnTouchOutside) {
        setCanceledOnTouchOutside(canceledOnTouchOutside);
        return show(defaultWidthDP);
    }

    /**
     * 指定宽度
     */
    public AlertDialog show(int widthDp, boolean canceledOnTouchOutside) {

        if (dialog == null) {
            dialog = dialogBuilder.create();
        }

        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);

        if (!dialog.isShowing()) {
            dialog.show();

            if (widthDp > 0) {
                WindowManager.LayoutParams params =
                        dialog.getWindow().getAttributes();
                params.width = dip2Px(dialog.getContext(), widthDp);
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(params);
            }

            Button btnPositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button btnNegative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            Button btnNeutral = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

            if (btnPositive != null) {
                btnPositive.setTextColor(Color.parseColor("#DC254D"));
            }

            if (btnNegative != null) {
                btnNegative.setTextColor(Color.parseColor("#DC254D"));
            }

            if (btnNeutral != null) {
                btnNeutral.setTextColor(Color.parseColor("#DC254D"));
            }

        }

        return dialog;
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * dip转像素
     */
    public static int dip2Px(Context context, float dip) {
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return px;
    }
}
