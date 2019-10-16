package com.seagalputra.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.seagalputra.moviecatalogue.model.Movie;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    private Movie movie;

    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvDescription;
    private ImageView imgDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

    public void viewDetailMovie(Movie movie) {
        tvTitle.setText(movie.getTitle());
        tvDate.setText(movie.getDate());
        tvDescription.setText(movie.getDescription());
        Glide.with(this)
                .load(movie.getPhoto())
                .apply(new RequestOptions().override(500))
                .into(imgDetail);
    }
}
