package com.seagalputra.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.Toast;

import com.seagalputra.moviecatalogue.adapter.ListMovieAdapter;
import com.seagalputra.moviecatalogue.model.Movie;
import com.seagalputra.moviecatalogue.presenter.MainPresenter;
import com.seagalputra.moviecatalogue.view.MainView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView {
    private RecyclerView rvMovies;

    private String[] moviesTitle;
    private String[] moviesDate;
    private String[] moviesDescription;
    private TypedArray moviesPhoto;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovies = findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
        prepareData();

        final MainPresenter presenter = new MainPresenter(this);
        movies = presenter.addMovieData(moviesTitle, moviesDate, moviesDescription, moviesPhoto);
        presenter.showListData(movies);
    }

    private void prepareData() {
        moviesTitle = getResources().getStringArray(R.array.movies_title);
        moviesDate = getResources().getStringArray(R.array.movies_date);
        moviesDescription = getResources().getStringArray(R.array.movies_description);
        moviesPhoto = getResources().obtainTypedArray(R.array.movies_photo);
    }

    @Override
    public void showListMovie(ArrayList<Movie> movies) {
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(movies);
        rvMovies.setAdapter(listMovieAdapter);
        rvMovies.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        listMovieAdapter.setOnItemClickCallback(new ListMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                navigateToDetail(movie);
            }
        });
    }

    @Override
    public void navigateToDetail(Movie movie) {
        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(detailIntent);
    }
}
