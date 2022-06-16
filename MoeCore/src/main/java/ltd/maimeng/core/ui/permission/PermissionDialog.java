package ltd.maimeng.core.ui.permission;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import ltd.maimeng.core.R;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/08/13
 *     desc   :
 * </pre>
 */
public class PermissionDialog extends AlertDialog {

    // 上下文
    private Context mContext;
    private List<PermissionInfo> permissionInfos;
    // 内容的父节点，用于内容较多时可以滚动
    private ScrollView mListScroll;
    // 内容
    private LinearLayout mListLayout;
    // 确认按钮
    private TextView mRefuseBtn;
    private TextView mConfirmBtn;

    public PermissionDialog(Context context, List<PermissionInfo> permissionInfos) {
        // 设置对话框样式
        super(context, R.style.style_permission_dialog);

        // 设置为false，按对话框以外的地方不起作用
        setCanceledOnTouchOutside(false);
        // 设置为false，按返回键不能退出
        setCancelable(false);

        this.mContext = context;
        this.permissionInfos = permissionInfos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_dialog_layout);

        initViews();
        initDatas();
        initEvents();
    }

    /**
     * 初始化view
     */
    private void initViews() {
        mListScroll = findViewById(R.id.permission_dialog_list_scroll);
        mListLayout = findViewById(R.id.layout_list);
        mRefuseBtn = findViewById(R.id.dialog_refuse_btn);
        mConfirmBtn = findViewById(R.id.dialog_confirm_btn);
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        // 获取图标的颜色值
        int mFilterColor = ContextCompat.getColor(mContext, R.color.permission_dialog_img_color);
        int blue = Color.blue(mFilterColor);
        int green = Color.green(mFilterColor);
        int red = Color.red(mFilterColor);
        float[] cm = new float[]{
                1, 0, 0, 0, red,// 红色值
                0, 1, 0, 0, green,// 绿色值
                0, 0, 1, 0, blue,// 蓝色值
                0, 0, 0, 1, 1 // 透明度
        };
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(cm);

        // 初始化权限列表区域
        // int[] permissionImgIds = mContext.getResources().getIntArray(R.array.permission_icon);
        // String[] permissionTitles = mContext.getResources().getStringArray(R.array.permission_title);
        // String[] permissionInfos = mContext.getResources().getStringArray(R.array.permission_info);

        for (int i = 0; i < permissionInfos.size(); i++) {
            PermissionInfo permissionInfo = permissionInfos.get(i);
            int iconResID = permissionInfo.iconResID;
            // String title = permissionTitles[i];
            String title = permissionInfo.title;
            // String info = permissionInfos[i];
            String info = permissionInfo.desc;

            View itemView = View.inflate(mContext, R.layout.permission_list_item, null);
            ((ImageView) itemView.findViewById(R.id.item_img)).setImageResource(mContext.getResources().obtainTypedArray(R.array.permission_icon).getResourceId(i, 0));
            // ((ImageView) itemView.findViewById(R.id.item_img)).setImageResource(iconResID);
            ((ImageView) itemView.findViewById(R.id.item_img)).setColorFilter(filter);//设置图标的颜色
            ((TextView) itemView.findViewById(R.id.item_title)).setText(title);
            ((TextView) itemView.findViewById(R.id.item_info)).setText(info);

            mListLayout.addView(itemView);
        }

        // 设置内容区域的父节点的高度和内容文本大小
        final DisplayMetrics display = new DisplayMetrics();
        ((Activity) this.mContext).getWindowManager().getDefaultDisplay().getMetrics(display);
        final int screenHeight = display.heightPixels;
        // 先设置宽度，然后再调整高度
        mListScroll.setLayoutParams(new LinearLayout.LayoutParams(display.widthPixels - mContext.getResources().getDimensionPixelOffset(R.dimen.permission_dialog_margin) * 2, ViewGroup.LayoutParams.WRAP_CONTENT));

        //runnable中的方法会在View的measure、layout等事件后触发
        mListScroll.post(new Runnable() {
            @Override
            public void run() {
                if (mListScroll.getMeasuredHeight() > screenHeight / 2) {
                    mListScroll.setLayoutParams(new LinearLayout.LayoutParams(display.widthPixels - mContext.getResources().getDimensionPixelOffset(R.dimen.permission_dialog_margin) * 2, screenHeight / 2));
                } else {
                    // 屏幕宽度减去外边距*2
                    mListScroll.setLayoutParams(new LinearLayout.LayoutParams(display.widthPixels - mContext.getResources().getDimensionPixelOffset(R.dimen.permission_dialog_margin) * 2, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }
        });
    }

    /**
     * 初始化监听事件
     */
    private void initEvents() {
        mRefuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onDenyButtonClick();
                }
                dismiss();
            }
        });
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onAgreeButtonClick();
                }
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public interface OnButtonClickListener {
        // 确认按钮的点击事件接口
        void onAgreeButtonClick();
        void onDenyButtonClick();
    }

    private OnButtonClickListener mOnButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener mOnButtonClickListener) {
        this.mOnButtonClickListener = mOnButtonClickListener;
    }
}
