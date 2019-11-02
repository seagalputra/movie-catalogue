package com.seagalputra.moviecatalogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.seagalputra.moviecatalogue.db.contract.DatabaseContract;
import com.seagalputra.moviecatalogue.repository.Repository;
import com.seagalputra.moviecatalogue.repository.RepositoryImpl;
import com.seagalputra.moviecatalogue.entity.Movie;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_POSITION = "extra_position";

    public static final String MOVIE_TABLE = DatabaseContract.MOVIE_TABLE_NAME;

    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvDescription;
    private ImageView imgDetail;

    private Movie movie;
    private Repository repository;
    private boolean isFavorite;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        repository = RepositoryImpl.getInstance(getApplicationContext());

        tvTitle = findViewById(R.id.tv_title_detail);
        tvDate = findViewById(R.id.tv_date_detail);
        tvDescription = findViewById(R.id.tv_description_detail);
        imgDetail = findViewById(R.id.img_detail);
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        checkFavorite(movie);

        viewDetailMovie(movie);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movie.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.make_favorite:
                isFavorite = true;
                setFavoriteMovie(movie);
                supportInvalidateOptionsMenu();
                break;
            case R.id.make_unfavorite:
                isFavorite = false;
                removeFromFavorite(movie);
                supportInvalidateOptionsMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem favorite = menu.findItem(R.id.make_favorite);
        MenuItem unfavorite = menu.findItem(R.id.make_unfavorite);

        favorite.setVisible(!isFavorite);
        unfavorite.setVisible(isFavorite);

        return true;
    }

    public void viewDetailMovie(Movie movie) {
        tvTitle.setText(movie.getTitle());
        tvDate.setText(movie.getDate());
        tvDescription.setText(movie.getDescription());
        Glide.with(this)
                .load(movie.getPhoto())
                .apply(new RequestOptions().override(500))
                .into(imgDetail);
    }

    public void setFavoriteMovie(Movie movie) {
        // set favorite data model
        movie.setIsFavorite(1);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseColumns.ID, movie.getId());
        values.put(DatabaseContract.DatabaseColumns.TYPE, movie.getType());
        values.put(DatabaseContract.DatabaseColumns.PHOTO, movie.getPhoto());
        values.put(DatabaseContract.DatabaseColumns.TITLE, movie.getTitle());
        values.put(DatabaseContract.DatabaseColumns.DATE, movie.getDate());
        values.put(DatabaseContract.DatabaseColumns.DESCRIPTION, movie.getDescription());
        values.put(DatabaseContract.DatabaseColumns.FAVORITE, movie.getIsFavorite());

        long result = repository.insert(MOVIE_TABLE, values);
        if (result > 0) {
            Toast.makeText(this, "Movie successfully added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Movie unsuccessfully added!", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeFromFavorite(Movie movie) {
        movie.setIsFavorite(0);

        long result = repository.deleteById(MOVIE_TABLE, String.valueOf(movie.getId()));
        if (result > 0) {
            Toast.makeText(this, "Movie successfully removed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Movie unsuccessfully removed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkFavorite(Movie movie) {
        Cursor cursor = repository.queryById(MOVIE_TABLE, String.valueOf(movie.getId()));
        if (cursor.getCount() != 0) {
            isFavorite = true;
        } else {
            isFavorite = false;
        }
    }
}
