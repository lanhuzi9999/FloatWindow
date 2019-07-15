package com.example.floatwindow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private FloatWindow mFloatWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFloatWindow = new FloatWindow(this);
        String testUrl = "file:///android_asset/7zhe.png";
        mFloatWindow.showWindow(testUrl, "");
    }

}
