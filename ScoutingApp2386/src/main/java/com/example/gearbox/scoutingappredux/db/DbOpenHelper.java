package com.example.gearbox.scoutingappredux.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gearbox on 02/02/16.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Team.db";
    public static final int DATABASE_VERSION = 4;


    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TeamDataSource.CREATE_TABLE);
        db.execSQL(TeamStatsDataSource.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TeamDataSource.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TeamStatsDataSource.TABLE_NAME);
        onCreate(db);
    }
}
