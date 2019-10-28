package com.seagalputra.moviecatalogue.service;

import android.database.Cursor;
import android.os.AsyncTask;

import com.seagalputra.moviecatalogue.db.contract.DatabaseContract;
import com.seagalputra.moviecatalogue.db.helper.MappingHelper;
import com.seagalputra.moviecatalogue.entity.Movie;
import com.seagalputra.moviecatalogue.repository.Repository;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private final WeakReference<Repository> weakRepositoryHelper;
    private final WeakReference<LoadDataCallback> weakCallback;

    public LoadTvShowAsync(Repository repository, LoadDataCallback callback) {
        weakRepositoryHelper = new WeakReference<>(repository);
        weakCallback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        weakCallback.get().preExecute();
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {
        Cursor tvShowCursor = weakRepositoryHelper.get().queryAll(DatabaseContract.TVSHOW_TABLE_NAME);
        return MappingHelper.mapCursorToArrayList(tvShowCursor);
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        weakCallback.get().postExecute(movies);
    }
}
