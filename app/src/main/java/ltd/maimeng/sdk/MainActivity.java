package ltd.maimeng.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ltd.maimeng.core.ui.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    public View setContentView() {
        return View.inflate(MainActivity.this, R.layout.activity_main, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_background_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BackgroundBlurActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_singleinstance_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SingleInstanceActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_screen_adaption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScreenAdaptionActivity.class);
                startActivity(intent);
            }
        });
    }
}
