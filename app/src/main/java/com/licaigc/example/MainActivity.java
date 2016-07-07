package com.licaigc.example;

import android.app.Activity;
import android.os.Bundle;

import com.licaigc.AndroidBaseLibrary;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidBaseLibrary.initialize(getApplicationContext());
    }
}
