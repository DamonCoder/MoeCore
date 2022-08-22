package ltd.maimeng.core.ui.status;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import ltd.maimeng.core.R;
import ltd.maimeng.core.utils.TextUtil;

public class LoadingDialog extends Dialog {

    private TextView tvDesc;

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.dialog_loading);
        tvDesc = findViewById(R.id.tv_loadding);
    }

    public void setLoadingDesc(String strDesc) {
        if (TextUtil.isEmpty(strDesc)) {
            tvDesc.setVisibility(View.GONE);
        } else {
            tvDesc.setText(strDesc);
            tvDesc.setVisibility(View.VISIBLE);
        }
    }
}
