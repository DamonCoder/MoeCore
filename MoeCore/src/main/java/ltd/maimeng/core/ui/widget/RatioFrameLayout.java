package ltd.maimeng.core.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import ltd.maimeng.core.R;


public class RatioFrameLayout extends FrameLayout {

    private int mRatioWidth;
    private int mRatioHeight;

    public RatioFrameLayout(Context context) {
        this(context, null, 0);
    }

    public RatioFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout, defStyle, 0);
        mRatioWidth = typedArray.getInt(R.styleable.RatioLayout_ratioWidth, 0);
        mRatioHeight = typedArray.getInt(R.styleable.RatioLayout_ratioHeight, 0);
        if (mRatioWidth == 0 || mRatioHeight == 0) {
            throw new IllegalArgumentException("can't equal 0");
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width, height;
        if (mRatioWidth != 0 && mRatioHeight != 0) {
            width = widthSize;
            height = (int) (width * ((float) mRatioHeight / mRatioWidth));
            int exactWidthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            int exactHeightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            super.onMeasure(exactWidthSpec, exactHeightSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}