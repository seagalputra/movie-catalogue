package com.seagalputra.moviecatalogue.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.seagalputra.moviecatalogue.BuildConfig;
import com.seagalputra.moviecatalogue.entity.Movie;
import com.seagalputra.moviecatalogue.entity.MovieBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listTvShow = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> listFavoriteTvShow = new MutableLiveData<>();

    public static final int TVSHOW_TYPE = 2;

    public void setTvShow(String language) {
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = BuildConfig.TvShowURL + language;
        final String posterUrl = "https://image.tmdb.org/t/p/w500";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    parseTvShow(listItems, posterUrl, responseBody);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    private void parseTvShow(ArrayList<Movie> listItems, String posterUrl, byte[] responseBody) throws JSONException {
        String result = new String(responseBody);
        JSONObject responseObject = new JSONObject(result);
        JSONArray listResults = responseObject.getJSONArray("results");
        for (int i = 0; i < listResults.length(); i++) {
            JSONObject item = listResults.getJSONObject(i);
            Movie tvShow = getTvShow(item, posterUrl);
            listItems.add(tvShow);
        }
        listTvShow.postValue(listItems);
    }

    private Movie getTvShow(JSONObject item, String posterUrl) throws JSONException {
        String posterPath = posterUrl + item.getString("poster_path");
        return new MovieBuilder(item.getInt("id"), item.getString("original_name"))
                .withDate(item.getString("first_air_date"))
                .withDescription(item.getString("overview"))
                .withPhoto(posterPath)
                .withType(TVSHOW_TYPE)
                .withFavorite(0)
                .build();
    }

    public LiveData<ArrayList<Movie>> getTvShows() {
        return listTvShow;
    }

    public LiveData<ArrayList<Movie>> getFavoriteTvShows() {
        return listFavoriteTvShow;
    }

    public void setFavoriteTvShow(ArrayList<Movie> movies) {
        this.listFavoriteTvShow.postValue(movies);
    }
}
