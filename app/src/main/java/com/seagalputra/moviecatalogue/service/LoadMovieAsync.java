package com.seagalputra.moviecatalogue.service;

import android.database.Cursor;
import android.os.AsyncTask;

import com.seagalputra.moviecatalogue.db.contract.DatabaseContract;
import com.seagalputra.moviecatalogue.db.helper.MappingHelper;
import com.seagalputra.moviecatalogue.repository.Repository;
import com.seagalputra.moviecatalogue.entity.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private final WeakReference<Repository> weakRepositoryHelper;
    private final WeakReference<LoadDataCallback> weakCallback;

    public LoadMovieAsync(Repository repository, LoadDataCallback callback) {
        this.weakRepositoryHelper = new WeakReference<>(repository);
        this.weakCallback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        weakCallback.get().preExecute();
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... voids) {
        Cursor movieCursor = weakRepositoryHelper.get().queryByType(DatabaseContract.MOVIE_TABLE_NAME, "1");
        return MappingHelper.mapCursorToArrayList(movieCursor);
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        weakCallback.get().postExecute(movies);
    }
}
