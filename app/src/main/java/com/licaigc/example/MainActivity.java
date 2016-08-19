package com.licaigc.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.licaigc.AndroidBaseLibrary;
import com.licaigc.update.UpdateUtils;

public class MainActivity extends Activity {
    public static final String TAG = "MainActivity";

    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUtils.checkUpdate(MainActivity.this, "123", new UpdateUtils.OnCheckUpdate() {
                    @Override
                    public void onFinish(boolean okOrCancel) {
                        Toast.makeText(MainActivity.this, String.valueOf(okOrCancel), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        AndroidBaseLibrary.initialize(getApplicationContext());

        mBtn.post(new Runnable() {
            @Override
            public void run() {
                mBtn.performClick();
            }
        });

    }
}
