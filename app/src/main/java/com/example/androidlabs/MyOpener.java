package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "ChatDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "CHAT";
    public final static String COL_MSG = "MESSAGE";
    public final static String COL_ID = "_id";
    public final static String COL_IS_SEND = "IS_SEND";

    public MyOpener(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MSG + " text,"
                + COL_IS_SEND + " text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}