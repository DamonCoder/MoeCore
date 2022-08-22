package ltd.maimeng.sdk.module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ltd.maimeng.core.log.MoeLog;
import ltd.maimeng.core.ui.BaseActivity;
import ltd.maimeng.sdk.R;
import ltd.maimeng.sdk.module.blur.BackgroundBlurActivity;
import ltd.maimeng.sdk.module.live.LiveRoomActivity;


public class MainActivity extends BaseActivity {

    @Override
    public View setContentView() {
        return View.inflate(MainActivity.this, R.layout.activity_main, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoeLog.i("Coder", "MainActivity.onCreate");

        findViewById(R.id.btn_background_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BackgroundBlurActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_live_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LiveRoomActivity.class);
                intent.putExtra(LiveRoomActivity.MAIN_TASK_ID, getTaskId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
        });

        findViewById(R.id.btn_screen_adaption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScreenAdaptionActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_loadding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoaddingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MoeLog.i("Coder", "MainActivity.onNewIntent");
    }
}
