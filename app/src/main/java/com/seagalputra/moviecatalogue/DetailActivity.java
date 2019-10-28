package com.seagalputra.moviecatalogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
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

import static com.seagalputra.moviecatalogue.viewmodel.MovieViewModel.MOVIE_TYPE;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String MOVIE_TABLE = DatabaseContract.MOVIE_TABLE_NAME;
    public static final String TVSHOW_TABLE = DatabaseContract.TVSHOW_TABLE_NAME;

    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvDescription;
    private ImageView imgDetail;

    private Movie movie;
    private Repository repository;

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

        viewDetailMovie(movie);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movie.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.make_favorite) {
            if (movie.getType() == MOVIE_TYPE) {
                setFavoriteMovie(movie);
            } else {
                setFavoriteTvShow(movie);
            }
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseColumns.ID, movie.getId());
        values.put(DatabaseContract.DatabaseColumns.TYPE, movie.getType());
        values.put(DatabaseContract.DatabaseColumns.PHOTO, movie.getPhoto());
        values.put(DatabaseContract.DatabaseColumns.TITLE, movie.getTitle());
        values.put(DatabaseContract.DatabaseColumns.DATE, movie.getDate());
        values.put(DatabaseContract.DatabaseColumns.DESCRIPTION, movie.getDescription());

        long result = repository.insert(MOVIE_TABLE, values);
        if (result > 0) {
            Toast.makeText(this, "Movie successfully added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Movie unseccessfully added!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setFavoriteTvShow(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseColumns.ID, movie.getId());
        values.put(DatabaseContract.DatabaseColumns.TYPE, movie.getType());
        values.put(DatabaseContract.DatabaseColumns.PHOTO, movie.getPhoto());
        values.put(DatabaseContract.DatabaseColumns.TITLE, movie.getTitle());
        values.put(DatabaseContract.DatabaseColumns.DATE, movie.getDate());
        values.put(DatabaseContract.DatabaseColumns.DESCRIPTION, movie.getDescription());
        
        long result = repository.insert(TVSHOW_TABLE, values);
        if (result > 0) {
            Toast.makeText(this, "Tv Show successfully added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tv Show unsuccessfully added!", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeFromFavorite(Movie movie) {
        // TODO : Check and remove item from database
    }
}
