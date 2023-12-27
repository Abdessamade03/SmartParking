package com.example.smartparking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_PATH = "F:/DOCUMENTS/SmartParking/SmartParking/UsersDB.db";
    private static final String DATABASE_NAME = "UsersDB.db";
    private static final int DATABASE_VERSION = 1;

    // Define your table creation query here
    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS SmartParkingUsers " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_PATH + DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
