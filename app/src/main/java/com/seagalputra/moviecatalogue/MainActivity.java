package com.seagalputra.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.seagalputra.moviecatalogue.adapter.MovieAdapter;
import com.seagalputra.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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
        movieAdapter = new MovieAdapter(this);
        listView.setAdapter(movieAdapter);
        prepareData();
        addMovieData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(MainActivity.this, movies.get(i).getTitle(), Toast.LENGTH_SHORT).show();
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

    public void addMovieData() {
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
}
