package ltd.maimeng.core.ui.status;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;
import ltd.maimeng.core.R;
import ltd.maimeng.core.ui.permission.PermissionActivity;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/10
 *     desc   : 状态相关的PraentActivity（加载中、数据正常/无数据、网络异常等）
 * </pre>
 */
public abstract class StatusActivity extends PermissionActivity implements StatusBehavior {

    private StatusLayout statusLayout;
    private RelativeLayout loadingLayout;
    private LinearLayout loadingLayoutContent;
    private ImageView loadingLayoutContentGif;
    private TextView loadingLayoutContentTxt;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);

            View decor = window.getDecorView();
            if (lightStatusBar()) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        statusLayout = findViewById(R.id.activity_status_layout);
        statusLayout.setContentView(setContentView());
        showContentLayout();

        loadingLayout = findViewById(R.id.activity_loading);
        loadingLayoutContent = findViewById(R.id.activity_loading_content);
        loadingLayoutContentGif = findViewById(R.id.activity_loading_content_gif);
        loadingLayoutContentTxt = findViewById(R.id.activity_loading_content_txt);
        Glide.with(StatusActivity.this).load(R.drawable.ic_loading).into(loadingLayoutContentGif);

        loadingDialog = new LoadingDialog(StatusActivity.this, R.style.LoadingDialog);
    }

    public boolean lightStatusBar() {
        return true;
        // return false;
    }

    public abstract View setContentView();

    @Override
    public void showLoadingLayout() {
        statusLayout.showLoading();
    }

    @Override
    public void showContentLayout() {
        statusLayout.showContent();
    }

    @Override
    public void showNetErrorLayout() {
        statusLayout.showNetError();
    }

    public void showLoadding() {
        showLoadding("");
    }

    protected void showLoadding(String loaddingDesc) {
        // if (loadingLayout.getVisibility() == View.GONE) {
        //     loadingLayout.setVisibility(View.VISIBLE);
        // }
        // if (TextUtil.isEmpty(loaddingDesc)) {
        //     // loadingLayoutContent.setBackground(null);
        //     loadingLayoutContent.setBackgroundResource(R.drawable.shape_loadding_bg);
        //     loadingLayoutContentTxt.setVisibility(View.GONE);
        // } else {
        //     loadingLayoutContent.setBackgroundResource(R.drawable.shape_loadding_bg);
        //     loadingLayoutContentTxt.setText(loaddingDesc);
        //     loadingLayoutContentTxt.setVisibility(View.VISIBLE);
        // }
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
            loadingDialog.setLoadingDesc(loaddingDesc);
        }
    }

    public void hideLoadding() {
        // if (loadingLayout.getVisibility() == View.VISIBLE) {
        //     loadingLayout.setVisibility(View.GONE);
        // }
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (loadingLayout.getVisibility() == View.VISIBLE) {
                // 加载中不能返回
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
