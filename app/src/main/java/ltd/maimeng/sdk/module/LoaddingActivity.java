package ltd.maimeng.sdk.module;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.hjq.toast.ToastUtils;

import ltd.maimeng.core.ui.BaseActivity;
import ltd.maimeng.core.ui.dialog.MoeDialog;
import ltd.maimeng.sdk.R;

public class LoaddingActivity extends BaseActivity {

    @Override
    public View setContentView() {
        return View.inflate(LoaddingActivity.this, R.layout.activity_loadding, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_show_loadding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadding();

                Handler mDelivery = new Handler(Looper.getMainLooper());
                mDelivery.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadding();
                    }
                }, 5000);
            }
        });

        findViewById(R.id.btn_click_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("ClickTest");
            }
        });

        findViewById(R.id.btn_show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoeDialog.createOrigin(LoaddingActivity.this)
                        .setTitle("Dialog")
                        .setMessage("点击确认按钮加载Loadding")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("显示", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showLoadding("数据加载中...");

                                Handler mDelivery = new Handler(Looper.getMainLooper());
                                mDelivery.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideLoadding();
                                    }
                                }, 5000);
                            }
                        })
                        .show();
            }
        });
    }
}