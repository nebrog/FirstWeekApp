package com.example.firstweekapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {
    // Content Provider используется к примеру в WatsApp или Telegram
    // где из телефонной книги берутся данные контактов

    public MyContentProvider() {
    }

    // определение полномочий, чтобы другое приложение могло получить к нему доступ
    static final String TEST_CLIENT_MY_PROVIDER = "com.test.client.MyProvider";

    // определение содержимого URI
    static final String URL = "content://" + TEST_CLIENT_MY_PROVIDER + "/users";

    // парсинг содержимого URI
    static final Uri URI = Uri.parse(URL);

    static final String ID = "id";
    static final String NAME = "name";
    static final int URI_CODE = 1;
    static final UriMatcher uriMatcher;
    private static HashMap<String, String> values;

    static {

        // соответсвие содержимого URI
        //каждый раз, когда пользователь обращается к таблице
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // получение доступа ко всей таблицы
        uriMatcher.addURI(TEST_CLIENT_MY_PROVIDER, "users", URI_CODE);

        // получение доступа
        // к определенной строке таблицы
        uriMatcher.addURI(TEST_CLIENT_MY_PROVIDER, "users/*", URI_CODE);
    }
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_CODE:
                return "vnd.android.cursor.dir/users";
            default:
                throw new IllegalArgumentException("Неподдерживаемый URI: " + uri);
        }
    }
    // создаем базу данных
    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(table);
        switch (uriMatcher.match(uri)) {
            case URI_CODE:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = ID;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    // добавление данных в базу данных
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(table, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(MyContentProvider.URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLiteException("Не удалось добавить запись в " + uri);
    }

    //обновление данных в базу данных в БД
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case URI_CODE:
                count = db.update(table, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    // удаление данных в базу данных в БД
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case URI_CODE:
                count = db.delete(table, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    // создание объекта базы данных
    // для выполнения запроса
    private SQLiteDatabase db;

    // объявление имени базы данных
    static final String dbName = "UserDB";

    // объявление имени таблицы базы данных
    static final String table = "Users";

    // объявление версии базы данных
    static final int DATABASE_VERSION = 1;

    // sql-запрос для создания таблицы
    static final String createDB = " CREATE TABLE " + table
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " name TEXT NOT NULL);";

    // создание базы данных
    private static class DatabaseHelper extends SQLiteOpenHelper {

        // определение конструктора
        DatabaseHelper(Context context) {
            super(context, dbName, null, DATABASE_VERSION);
        }

        // создание таблицы в базе данных
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(createDB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            // sql-запрос для удаления таблицы
            // имеющий похожее имя
            db.execSQL("DROP TABLE IF EXISTS " + table);
            onCreate(db);
        }
    }
}