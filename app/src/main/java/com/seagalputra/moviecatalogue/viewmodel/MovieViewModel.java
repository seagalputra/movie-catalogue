package com.seagalputra.moviecatalogue.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.seagalputra.moviecatalogue.BuildConfig;
import com.seagalputra.moviecatalogue.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public void setMovie(String language) {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.ApiKey + "&language=" + language;
        final String posterUrl = "https://image.tmdb.org/t/p/w500";
        final ArrayList<Movie> listItems = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    parseMovie(listItems, posterUrl, responseBody);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("OnFailure", error.getMessage());
            }
        });
    }

    public Movie getMovie(JSONObject movieItem, String url) throws JSONException {
        String posterPath = url + movieItem.getString("poster_path");
        Movie movie = new Movie.MovieBuilder(movieItem.getInt("id"), movieItem.getString("original_title"))
                .withDate(movieItem.getString("release_date"))
                .withDescription(movieItem.getString("overview"))
                .withPhoto(posterPath)
                .build();

        return movie;
    }

    public void parseMovie(ArrayList<Movie> listItems, String posterUrl, byte[] responseBody) throws JSONException {
        String result = new String(responseBody);
        JSONObject responseObject = new JSONObject(result);
        JSONArray listResults = responseObject.getJSONArray("results");
        for (int i = 0; i < listResults.length(); i++) {
            JSONObject item = listResults.getJSONObject(i);
            Movie movie = getMovie(item, posterUrl);
            listItems.add(movie);
        }
        listMovies.postValue(listItems);
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }
}
