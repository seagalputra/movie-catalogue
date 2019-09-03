package com.seagalputra.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ListView;

import com.seagalputra.moviecatalogue.adapter.MovieAdapter;
import com.seagalputra.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] moviesTitle;
    private String[] moviesDate;
    private String[] moviesDescription;
    private TypedArray moviesPhoto;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieAdapter = new MovieAdapter(this);
        ListView listView = findViewById(R.id.lv_movie);
        listView.setAdapter(movieAdapter);

        prepare();
        addMovies();
    }

    private void addMovies() {
        movies = new ArrayList<>();

        for (int i = 0; i < moviesTitle.length; i++) {
            Movie movie = new Movie();
            movie.setPhoto(moviesPhoto.getResourceId(i, -1));
            movie.setTitle(moviesTitle[i]);
            movie.setDate(moviesDate[i]);
            movie.setDescription(moviesDescription[i]);
            movies.add(movie);
        }
        movieAdapter.setMovies(movies);
    }

    private void prepare() {
        moviesTitle = getResources().getStringArray(R.array.data_title);
        moviesDate = getResources().getStringArray(R.array.data_date);
        moviesDescription = getResources().getStringArray(R.array.data_description);
        moviesPhoto = getResources().obtainTypedArray(R.array.data_photo);
    }
}
