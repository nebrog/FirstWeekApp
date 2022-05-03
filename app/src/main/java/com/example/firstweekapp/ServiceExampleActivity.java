package com.example.firstweekapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class ServiceExampleActivity extends AppCompatActivity {

    // создание активити и инициализаця переменных Button
    // к переменным Button вешается ClickListener
    // который запускает сервис, либо уничтожает его

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_example);
        Button buttonStart = findViewById(R.id.service_start);
        Button buttonStop = findViewById(R.id.service_stop);
        View.OnClickListener clickListenerStart = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceExampleActivity.this, MyService.class);
                startService(intent);

            }
        };
        buttonStart.setOnClickListener(clickListenerStart);
        View.OnClickListener clickListenerStop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceExampleActivity.this, MyService.class);
                stopService(intent);
            }
        };
        buttonStop.setOnClickListener(clickListenerStop);
    }
}