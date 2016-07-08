package com.licaigc.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.licaigc.AndroidBaseLibrary;
import com.licaigc.statistics.Statistics;

public class MainActivity extends Activity {

    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(v -> Statistics.reportStartupInfo(1));

        AndroidBaseLibrary.initialize(getApplicationContext());

        mBtn.post(() -> mBtn.performClick());
    }
}
