package com.example.firstweekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // объявление переменных
    private Button serviceButton;
    private Button providerButton;
    private Button receiverButton;
    private Button activityButton;


    // создание активити и инициализаця кнопок
    // каждая кнопка открывает выбранный для демонстрации компонент
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceButton = findViewById(R.id.service_btn);
        providerButton = findViewById(R.id.provider_btn);
        receiverButton = findViewById(R.id.receiver_btn);
        activityButton = findViewById(R.id.activity_btn);
        MainActivity.this.ClickOnButton();




    }
    private void ClickOnButton(){
        View.OnClickListener activityClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ActivityExample.class);
                MainActivity.this.startActivity(intent);
            }
        };
        activityButton.setOnClickListener(activityClickListener);

        View.OnClickListener serviceClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ServiceExampleActivity.class);
                MainActivity.this.startActivity(intent);
            }
        };
        serviceButton.setOnClickListener(serviceClickListener);

        View.OnClickListener receiverClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ReceiverExampleActivity.class);
                MainActivity.this.startActivity(intent);
            }
        };
        receiverButton.setOnClickListener(receiverClickListener);

        View.OnClickListener providerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProviderExampleActivity.class);
                MainActivity.this.startActivity(intent);
            }
        };
        providerButton.setOnClickListener(providerClickListener);

    }
}