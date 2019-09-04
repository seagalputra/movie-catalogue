package com.seagalputra.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.seagalputra.moviecatalogue.adapter.MovieAdapter;
import com.seagalputra.moviecatalogue.model.Movie;
import com.seagalputra.moviecatalogue.presenter.MainPresenter;
import com.seagalputra.moviecatalogue.view.MainView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView {
    private ListView listView;
    private MovieAdapter movieAdapter;

    private String[] moviesTitle;
    private String[] moviesDate;
    private String[] moviesDescription;
    private TypedArray moviesPhoto;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv_movie);
        prepareData();

        final MainPresenter presenter = new MainPresenter(this);
        movies = presenter.addMovieData(moviesTitle, moviesDate, moviesDescription, moviesPhoto);
        presenter.showListData(movies);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(i));
                startActivity(detailIntent);
            }
        });
    }

    private void prepareData() {
        moviesTitle = getResources().getStringArray(R.array.data_title);
        moviesDate = getResources().getStringArray(R.array.data_date);
        moviesDescription = getResources().getStringArray(R.array.data_description);
        moviesPhoto = getResources().obtainTypedArray(R.array.data_photo);
    }

    @Override
    public void showListMovie(ArrayList<Movie> movies) {
        movieAdapter = new MovieAdapter(this);
        listView.setAdapter(movieAdapter);
        movieAdapter.setMovies(movies);
    }
}
