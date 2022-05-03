package com.example.firstweekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProviderExampleActivity extends AppCompatActivity {

    // объявление переменных
    private EditText editText;
    private Button insert;
    private Button load;
    private Button loadContacts;
    private TextView data;

    // создание активити и инициализаця переменных
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_example);
        editText = findViewById(R.id.textName);
        insert = findViewById(R.id.insertButton);
        load = findViewById(R.id.loadButton);
        data = findViewById(R.id.res);
        loadContacts = findViewById(R.id.loadContacts);
        onClickAddDetails();
        onClickShowDetails();
        onClickLoadContacts();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:

                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   bar();
                } else {
                    Toast.makeText(getBaseContext(), "Разрешите доступ к контактам, для предоставления информации", Toast.LENGTH_LONG).show();
                }
                return;
        }

    }


    //создание функции для добавления пользователем новых данных в БД
    private void onClickAddDetails() {
        View.OnClickListener clickListenerInsert = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // класс для добавления значений в базу данных
                ContentValues values = new ContentValues();
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    editText.setError("Данные не введены");
                    return;

                } else {

                    // получение текста от пользователя
                    values.put(MyContentProvider.NAME, editText.getText().toString());

                    // вставка в базу данных через URI
                    getContentResolver().insert(MyContentProvider.URI, values);

                    // отображение toast сообщения на экране
                    Toast.makeText(getBaseContext(), "Новые данные добавлены", Toast.LENGTH_LONG).show();

                }
            }
        };
        insert.setOnClickListener(clickListenerInsert);


    }

    // создание функции для просмотра содержимого в БД
    private void onClickShowDetails() {
        View.OnClickListener clickListenerLoad = new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                // создание объекта типа Cursor с URI содержимым
                Cursor cursor = getContentResolver().query(Uri.parse("content://com.test.client.MyProvider/users"), null, null, null, null);

                // итерация cursor
                // для печати всей таблицы целиком
                if (cursor.moveToFirst()) {
                    StringBuilder strBuild = new StringBuilder();
                    while (!cursor.isAfterLast()) {
                        strBuild.append("\n" + cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("name")));
                        cursor.moveToNext();
                    }
                    data.setText(strBuild);
                } else {
                    data.setText("Данных нет");
                }

            }
        };
        load.setOnClickListener(clickListenerLoad);
    }

    private void onClickLoadContacts() {
        View.OnClickListener onClickListenerContacts = new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ProviderExampleActivity.this, Manifest.permission.READ_CONTACTS)
                        == PackageManager.PERMISSION_GRANTED) {
                    bar();
                } else {
                    ActivityCompat.requestPermissions(ProviderExampleActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 0);
                    return;
                }
            }
        };
        loadContacts.setOnClickListener(onClickListenerContacts);
    }
    // функция для демонстрации контактов телефона в логах
    @SuppressLint("Range")
    private void bar() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        Log.i("CONTACTS", "Колличество контактов: " + Integer.toString(cursor.getCount()));
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                Log.i("CONTACTS", "Контакт: " + contactName + " Номер телефона: " + contactNumber);
            }
        }

    }
}