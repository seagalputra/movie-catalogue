package com.seagalputra.moviecatalogue.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.seagalputra.moviecatalogue.BuildConfig;
import com.seagalputra.moviecatalogue.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> listTvShow = new MutableLiveData<>();

    public void setTvShow() {
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+ BuildConfig.ApiKey +"&language=en-US";
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
        Movie tvShow = new Movie.MovieBuilder(item.getInt("id"), item.getString("original_name"))
                .withDate(item.getString("first_air_date"))
                .withDescription(item.getString("overview"))
                .withPhoto(posterPath)
                .build();
        return tvShow;
    }

    public LiveData<ArrayList<Movie>> getTvShows() {
        return listTvShow;
    }
}
