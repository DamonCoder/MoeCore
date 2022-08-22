package ltd.maimeng.sdk.module.live;

import android.os.Bundle;
import android.view.View;

import ltd.maimeng.core.ui.BaseActivity;
import ltd.maimeng.sdk.R;

public class RoomFeedbackActivity extends BaseActivity {

    @Override
    public View setContentView() {
        return View.inflate(RoomFeedbackActivity.this, R.layout.activity_room_feedback, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}