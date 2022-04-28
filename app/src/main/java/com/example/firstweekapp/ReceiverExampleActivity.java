package com.example.firstweekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ReceiverExampleActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView batteryLevel;
    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_example);
        progressBar = findViewById(R.id.progress_bar);
        batteryLevel = findViewById(R.id.battery_level);
        broadcastReceiver = new MyBroadcast();
    }

    @Override
    protected void onStart() {
        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    public class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryLevel.setText(level + " %");
            progressBar.setProgress(level);

        }
    }
}