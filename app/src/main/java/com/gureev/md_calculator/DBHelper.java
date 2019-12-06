package com.gureev.md_calculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

   public static final int DATABASE_VERSION = 1;
   public static final String DATABASE_NAME = "MD_Calculator";
   public static final String TABLE_MAIN = "main";

   public static final String key = "_id";
   public static final String expression = "expression";
   public static final String result = "result";
   public static final String dataTime = "dataTime";
   public static final String lat = "lat";
   public static final String lon = "lon";
   //public static final String someValue = "someValue";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = String.format("create table %s " +
                "(" +
                "%s integer primary key," +
                "%s text, " +
                "%s text, " +
                "%s text, " +
                "%s text, " +
                "%s text " +
                ")",TABLE_MAIN,key, expression,result,dataTime,lat,lon);
        db.execSQL(create);
        Log.d("Database Operations","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ TABLE_MAIN);
        onCreate(db);
    }
}
