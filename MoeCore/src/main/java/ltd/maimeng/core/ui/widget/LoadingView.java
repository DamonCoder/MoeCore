package ltd.maimeng.core.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import ltd.maimeng.core.R;

import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/13
 *     desc   :
 * </pre>
 */
public class LoadingView extends View {

    private Resources mResource;
    private Paint mPaint;
    private Paint mOvalPaint;

    private int mRadius;
    private int mDistance;
    private int mOvalTop;
    private int mOvalHeight;
    private int mOvalWidth;

    private int mCenterX, mCenterY;
    private int currentCenterY;
    private Animator mRotationAnim;
    private ValueAnimator mLineAnimDown;
    private ValueAnimator mLineAnimUp;
    private Shape shape;

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mResource = context.getResources();
        init();
    }

    private void init() {
        mRadius = 60;
        mDistance = 300;
        mOvalHeight = 18;
        mOvalWidth = 60;
        mOvalTop = 150;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mResource.getColor(R.color.color_F88B40));

        mOvalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOvalPaint.setStyle(Paint.Style.FILL);
        mOvalPaint.setColor(mResource.getColor(R.color.color_FDB549));

        setupAnimations();
        shape = Shape.RECT;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 宽度最长是矩形的宽,高度最高是矩形的高加上distance
         */
        int width = mRadius * 2;
        int height = mDistance + mRadius * 2 + mOvalTop + mOvalHeight;
        setMeasuredDimension(width, height);
        currentCenterY = mCenterX = mCenterY = mRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (shape) {
            case RECT:
                drawRect(canvas);
                break;
            case TRAIL:
                drawTrail(canvas);
                break;
            case CIRCLE:
                drawCircle(canvas);
                break;
        }
        drawOval(canvas);
    }

    /**
     * 更具中心点的改变，绘制矩形
     */
    private void drawRect(Canvas canvas) {
        canvas.drawRect(mCenterX - mRadius, currentCenterY - mRadius, mCenterX + mRadius, currentCenterY + mRadius, mPaint);
    }

    private void drawTrail(Canvas canvas) {
        Path path = new Path();
        int leftX = 0;
        int leftY = currentCenterY + mRadius;
        int middleX = mCenterX;
        int middleY = currentCenterY - mRadius;
        int rightX = mCenterX + mRadius;
        int rightY = currentCenterY + mRadius;
        path.moveTo(leftX, leftY);
        path.lineTo(middleX, middleY);
        path.lineTo(rightX, rightY);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mCenterX, currentCenterY, mRadius, mPaint);
    }

    private void drawOval(Canvas canvas) {
        float factory = ((mDistance + mRadius) - currentCenterY) / (float) mDistance;
        RectF rectF = new RectF(
                mCenterX - mOvalWidth * factory,
                mDistance + mRadius * 2 + mOvalTop,
                mCenterX + mOvalWidth * factory,
                mDistance + mRadius * 2 + mOvalTop + mOvalHeight
        );
        canvas.drawOval(rectF, mOvalPaint);
    }

    private void setupAnimations() {
        mRotationAnim = ValueAnimator.ofInt(0, 180);

        mLineAnimDown = ValueAnimator.ofInt(mCenterY + mRadius, mDistance);
        mLineAnimDown.setInterpolator(new AccelerateInterpolator(1.2f));
        mLineAnimDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentCenterY = (Integer) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        mLineAnimUp = ValueAnimator.ofInt(mDistance, mCenterY + mRadius);
        mLineAnimUp.setInterpolator(new DecelerateInterpolator(1.2f));
        mLineAnimUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentCenterY = (Integer) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        mLineAnimDown.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (shape == Shape.RECT) {
                    mPaint.setColor(mResource.getColor(R.color.color_F88B40));
                    shape = Shape.TRAIL;
                } else {
                    if (shape == Shape.TRAIL) {
                        mPaint.setColor(mResource.getColor(R.color.color_4BBBFF));
                        shape = Shape.CIRCLE;
                    } else {
                        mPaint.setColor(mResource.getColor(R.color.color_F57CA8));
                        shape = Shape.RECT;
                    }
                }
            }
        });

        final AnimatorSet set = new AnimatorSet();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                set.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
        set.playSequentially(mLineAnimDown, mLineAnimUp);
        set.setDuration(300);
        set.setStartDelay(100);
        set.start();
    }

    private enum Shape {
        RECT, TRAIL, CIRCLE;
    }
}
