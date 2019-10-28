package com.seagalputra.moviecatalogue.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.seagalputra.moviecatalogue.db.contract.DatabaseContract;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbmovieapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s" +
            " (%s INTEGER PRIMARY KEY," +
            " %s TEXT NOT NULL," +
            " %s INTEGER NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT)",
            DatabaseContract.MOVIE_TABLE_NAME,
            DatabaseContract.DatabaseColumns.ID,
            DatabaseContract.DatabaseColumns.PHOTO,
            DatabaseContract.DatabaseColumns.TYPE,
            DatabaseContract.DatabaseColumns.TITLE,
            DatabaseContract.DatabaseColumns.DATE,
            DatabaseContract.DatabaseColumns.DESCRIPTION
    );

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT)",
            DatabaseContract.TVSHOW_TABLE_NAME,
            DatabaseContract.DatabaseColumns.ID,
            DatabaseContract.DatabaseColumns.PHOTO,
            DatabaseContract.DatabaseColumns.TYPE,
            DatabaseContract.DatabaseColumns.TITLE,
            DatabaseContract.DatabaseColumns.DATE,
            DatabaseContract.DatabaseColumns.DESCRIPTION
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MOVIE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TVSHOW_TABLE_NAME);
        onCreate(db);
    }
}
