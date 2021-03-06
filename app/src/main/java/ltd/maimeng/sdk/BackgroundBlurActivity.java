package ltd.maimeng.sdk;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import ltd.maimeng.core.ui.BaseActivity;

import androidx.annotation.Nullable;

public class BackgroundBlurActivity extends BaseActivity {

    private ImageView ivBgBlurContent00, ivBgBlurContent01, ivBgBlurContent02, ivBgBlurMask;
    private TextView tvColor01, tvColor02, tvColor03, tvColor04, tvColor05, tvColor06;
    private TextView tvColor11, tvColor12, tvColor13, tvColor14, tvColor15, tvColor16;

    @Override
    public View setContentView() {
        return View.inflate(BackgroundBlurActivity.this, R.layout.activity_background_blur, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String backgroundUrl = "https://dramakill-bucket.oss-cn-beijing.aliyuncs.com/playscript/2022/03/31/3f006380438a4a0580d28e72f0c9acda.jpg";
        // String backgroundUrl = "https://img0.baidu.com/it/u=3006535508,152842159&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889";

        ivBgBlurContent00 = findViewById(R.id.iv_background_blur_content_00);
        ivBgBlurContent01 = findViewById(R.id.iv_background_blur_content_01);
        ivBgBlurContent02 = findViewById(R.id.iv_background_blur_content_02);
        ivBgBlurMask = findViewById(R.id.iv_background_blur_mask);
        tvColor01 = findViewById(R.id.tv_background_color_01);
        tvColor02 = findViewById(R.id.tv_background_color_02);
        tvColor03 = findViewById(R.id.tv_background_color_03);
        tvColor04 = findViewById(R.id.tv_background_color_04);
        tvColor05 = findViewById(R.id.tv_background_color_05);
        tvColor06 = findViewById(R.id.tv_background_color_06);
        tvColor11 = findViewById(R.id.tv_background_color_11);
        tvColor12 = findViewById(R.id.tv_background_color_12);
        tvColor13 = findViewById(R.id.tv_background_color_13);
        tvColor14 = findViewById(R.id.tv_background_color_14);
        tvColor15 = findViewById(R.id.tv_background_color_15);
        tvColor16 = findViewById(R.id.tv_background_color_16);

        Glide.with(BackgroundBlurActivity.this).load(backgroundUrl).into(ivBgBlurContent00);
        Glide.with(BackgroundBlurActivity.this).asBitmap().load(backgroundUrl).addListener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                ivBgBlurContent01.setImageBitmap(blur(resource, 15));
                return true;
            }
        }).into(ivBgBlurContent01);
        Glide.with(BackgroundBlurActivity.this).asBitmap().load(backgroundUrl).addListener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                getBitmapColor(resource);
                return false;
            }
        }).into(ivBgBlurContent02);
    }

    private void getBitmapColor(Bitmap resource) {
//        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                // ?????????????????????
//                int darkMutedColor = palette.getDarkMutedColor(Color.BLUE);
//                tvColor01.setBackgroundColor(darkMutedColor);
//                tvColor11.setBackgroundColor(palette.getDarkMutedColor(Color.TRANSPARENT));
//                // ?????????????????????
//                int darkVibrantColor = palette.getDarkVibrantColor(Color.BLUE);
//                tvColor02.setBackgroundColor(darkVibrantColor);
//                tvColor12.setBackgroundColor(palette.getDarkVibrantColor(Color.TRANSPARENT));
//                // ?????????????????????
//                int lightVibrantColor = palette.getLightVibrantColor(Color.BLUE);
//                tvColor03.setBackgroundColor(lightVibrantColor);
//                tvColor13.setBackgroundColor(palette.getLightVibrantColor(Color.TRANSPARENT));
//                // ?????????????????????
//                int lightMutedColor = palette.getLightMutedColor(Color.BLUE);
//                tvColor04.setBackgroundColor(lightMutedColor);
//                tvColor14.setBackgroundColor(palette.getLightMutedColor(Color.TRANSPARENT));
//                // ????????????
//                int mutedColor = palette.getMutedColor(Color.BLUE);
//                tvColor05.setBackgroundColor(mutedColor);
//                tvColor15.setBackgroundColor(palette.getMutedColor(Color.TRANSPARENT));
//                int vibrantColor = palette.getVibrantColor(Color.BLUE);
//                tvColor06.setBackgroundColor(vibrantColor);
//                tvColor16.setBackgroundColor(palette.getVibrantColor(Color.TRANSPARENT));
//
////                // ????????????
////                if (palette == null) return;
////                // palette????????????????????????????????????????????????????????????????????????????????????????????????????????????
////                if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
////                    createLinearGradientBitmap(palette.getDarkVibrantColor(Color.TRANSPARENT), palette.getVibrantColor(Color.TRANSPARENT));
////                } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
////                    createLinearGradientBitmap(palette.getDarkMutedColor(Color.TRANSPARENT), palette.getMutedColor(Color.TRANSPARENT));
////                } else {
////                    createLinearGradientBitmap(palette.getLightMutedColor(Color.TRANSPARENT), palette.getLightVibrantColor(Color.TRANSPARENT));
////                }
//                // createLinearGradientBitmap(palette.getLightVibrantColor(Color.TRANSPARENT), palette.getVibrantColor(Color.TRANSPARENT));
//                // createLinearGradientBitmap(Color.parseColor("#80FFFFFF"), Color.parseColor("#80FFFFFF"));
//            }
//        });
    }

    // ???????????????????????????
    private void createLinearGradientBitmap(int darkColor, int color) {
        int bgColors[] = new int[2];
        bgColors[0] = darkColor;
        bgColors[1] = color;

        Bitmap bgBitmap = Bitmap.createBitmap(ivBgBlurMask.getWidth(), ivBgBlurMask.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas mCanvas = new Canvas();
        Paint mPaint = new Paint();
        mPaint.setAlpha(160);
        mCanvas.setBitmap(bgBitmap);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        LinearGradient gradient = new LinearGradient(0, 0, 0, bgBitmap.getHeight(), bgColors[0], bgColors[1], Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        RectF rectF = new RectF(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
        // mCanvas.drawRoundRect(rectF,16,16,mPaint); ??????????????????????????????
        mCanvas.drawRect(rectF, mPaint);
        Bitmap bgBlurBitmap = blur(bgBitmap, 25);
        ivBgBlurMask.setImageBitmap(bgBlurBitmap);
    }

    private Bitmap blur(Bitmap bitmap, float radius) {
        Bitmap screenshotBlurBitmap = Bitmap.createBitmap(bitmap);// ??????????????????
        RenderScript rs = RenderScript.create(this);// ????????????RenderScript??????
        ScriptIntrinsicBlur gaussianBlue = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));// ????????????????????????
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);// ?????????????????????????????????
        Allocation allOut = Allocation.createFromBitmap(rs, screenshotBlurBitmap);// ?????????????????????????????????
        gaussianBlue.setRadius(radius);// ???????????????????????????0f<radius<=25f
        gaussianBlue.setInput(allIn);// ????????????????????????
        gaussianBlue.forEach(allOut);// ??????????????????????????????????????????????????????????????????
        allOut.copyTo(screenshotBlurBitmap);// ????????????????????????Bitmap???????????????????????????
        rs.destroy();// ??????RenderScript?????????API>=23?????????rs.releaseAllContexts()
        return screenshotBlurBitmap;
    }
}