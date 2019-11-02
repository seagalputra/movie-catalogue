package com.seagalputra.moviecatalogue.repository;

import android.content.ContentValues;
import android.database.Cursor;

public interface Repository {
    void open();
    void close();
    Cursor queryByType(String table, String type);
    long insert(String table, ContentValues values);
    int deleteById(String table, String id);
}
