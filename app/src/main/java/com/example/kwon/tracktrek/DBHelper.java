package com.example.kwon.tracktrek;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String sql = "create table if not exists memo("
                + "_id integer primary key autoincrement, "
                + "title text,"
                + "content test);";*/

        String sql = "create table if not exists memo("
                + "_id integer primary key autoincrement, "
                + "title text,"
                + "content text,"
                + "latitude integer,"
                + "longitude test);";

        db.execSQL(sql);
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String sql = "drop table if exists memo";
        db.execSQL(sql);

        onCreate(db);
    }
}
