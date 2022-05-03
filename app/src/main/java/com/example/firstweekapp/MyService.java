package com.example.firstweekapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class MyService extends Service {
    // Сервис используется в таких приложениях как Яндекс.Музыка, Youtube Premium, Spotify


    //создание сервиса
    @Override
    public void onCreate() {
        Toast.makeText(this, "Служба запущена",
                Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    // запуск сервиса
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        foo(startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //уничтожение сервиса
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();

    }
    // создание функции, в которой создается новый поток и живет 5 секунд
    private void foo(int startID) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                stopSelf(startID);
                Log.e("nebrog","после stopself " + startID);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

}