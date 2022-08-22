package ltd.maimeng.sdk.module.live;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import ltd.maimeng.core.log.MoeLog;
import ltd.maimeng.core.route.IntentValueCompat;
import ltd.maimeng.core.ui.BaseActivity;
import ltd.maimeng.sdk.R;

public class LiveRoomActivity extends BaseActivity {

    public static final String MAIN_TASK_ID = "main_task_id";

    @Override
    public View setContentView() {
        return View.inflate(LiveRoomActivity.this, R.layout.activity_live_room, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoeLog.i("Coder", "LiveRoomActivity.onCreate");

        findViewById(R.id.btn_room_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LiveRoomActivity.this, RoomFeedbackActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
        });

        findViewById(R.id.btn_room_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LiveRoomActivity.this, RoomSettingsActivity.class);
                MoeLog.i("Coder", "LiveRoomActivity=" + IntentValueCompat.getInt(LiveRoomActivity.MAIN_TASK_ID, getIntent()));
                intent.putExtra(MAIN_TASK_ID, IntentValueCompat.getInt(MAIN_TASK_ID, getIntent()));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MoeLog.i("Coder", "LiveRoomActivity.onNewIntent");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // finish();
            // overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

            moveTaskToBack(true);
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}