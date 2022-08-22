package ltd.maimeng.sdk.module.live;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import ltd.maimeng.core.log.MoeLog;
import ltd.maimeng.core.route.IntentValueCompat;
import ltd.maimeng.core.ui.BaseActivity;
import ltd.maimeng.sdk.R;

public class RoomSettingsActivity extends BaseActivity {

    @Override
    public View setContentView() {
        return View.inflate(RoomSettingsActivity.this, R.layout.activity_room_settings, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            finish();
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

            MoeLog.i("Coder", "RoomSettingsActivity=" + IntentValueCompat.getInt(LiveRoomActivity.MAIN_TASK_ID, getIntent()));
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            manager.moveTaskToFront(IntentValueCompat.getInt(LiveRoomActivity.MAIN_TASK_ID, getIntent()), 0);
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}