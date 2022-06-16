package ltd.maimeng.core.ui.status;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import ltd.maimeng.core.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/13
 *     desc   :
 * </pre>
 */
public class StatusLayout extends FrameLayout {

    private FrameLayout statusLoadingLayout;
    private FrameLayout statusContentLayout;
    private RelativeLayout statusNetErrorLayout;

    public StatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    //     super(context, attrs, defStyleAttr, defStyleRes);
    //     init(context);
    // }

    private void init(Context context) {
        View.inflate(getContext(), R.layout.widget_status_layout, this);
        initLoadingLayout(context);
        initContentLayout();
        initNetErrorLayout();
        switchStatusLayout(null);
    }

    private void initLoadingLayout(Context context) {
        statusLoadingLayout = findViewById(R.id.status_layout_loading);
        ImageView statusLoadingLayoutGif = statusLoadingLayout.findViewById(R.id.status_layout_loading_gif);
        Glide.with(context).load(R.drawable.ic_loading).into(statusLoadingLayoutGif);
    }

    private void initContentLayout() {
        statusContentLayout = findViewById(R.id.status_layout_content);

    }

    private void initNetErrorLayout() {
        statusNetErrorLayout = findViewById(R.id.status_layout_error_net);
        ImageView netErrorImg = statusNetErrorLayout.findViewById(R.id.status_error_net_img);
        netErrorImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击重试

            }
        });
    }

    private void switchStatusLayout(View statusLayout) {
        if (statusLoadingLayout != null) {
            statusLoadingLayout.setVisibility(statusLoadingLayout == statusLayout ? View.VISIBLE : View.GONE);
        }
        if (statusContentLayout != null) {
            statusContentLayout.setVisibility(statusContentLayout == statusLayout ? View.VISIBLE : View.GONE);
        }
        if (statusNetErrorLayout != null) {
            statusNetErrorLayout.setVisibility(statusNetErrorLayout == statusLayout ? View.VISIBLE : View.GONE);
        }
    }

    public void showLoading() {
        switchStatusLayout(statusLoadingLayout);
    }

    public void showContent() {
        switchStatusLayout(statusContentLayout);
    }

    public void showNetError() {
        switchStatusLayout(statusNetErrorLayout);
    }

    public void setContentView(View contentView) {
        statusContentLayout.addView(contentView);
    }
}
