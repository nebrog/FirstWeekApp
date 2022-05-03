package com.example.firstweekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class ActivityExample extends AppCompatActivity {
    // Активити чаще всего используют для UI части приложения
    // Вконтакте, Youtube, Сбербанк

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

    }
}