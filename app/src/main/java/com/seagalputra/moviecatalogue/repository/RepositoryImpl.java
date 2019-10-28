package com.seagalputra.moviecatalogue.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.seagalputra.moviecatalogue.db.contract.DatabaseContract;
import com.seagalputra.moviecatalogue.db.helper.DatabaseHelper;

public class RepositoryImpl implements Repository {

    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase database;

    private static RepositoryImpl INSTANCE;

    private RepositoryImpl(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static RepositoryImpl getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RepositoryImpl(context);
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    @Override
    public Cursor queryAll(String table) {
        return database.query(
                table,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.DatabaseColumns.ID + " ASC"
        );
    }

    @Override
    public long insert(String table, ContentValues values) {
        return database.insert(table, null, values);
    }

    @Override
    public int deleteById(String table, String id) {
        return database.delete(table, DatabaseContract.DatabaseColumns.ID + " = ?", new String[]{id});
    }
}
