package com.seagalputra.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.seagalputra.moviecatalogue.model.Movie;
import com.seagalputra.moviecatalogue.presenter.DetailPresenter;
import com.seagalputra.moviecatalogue.view.DetailView;

public class DetailActivity extends AppCompatActivity implements DetailView {
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

        final DetailPresenter presenter = new DetailPresenter(this);
        presenter.showDetailMovie(movie);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movie.getTitle());
        }
    }

    @Override
    public void viewDetailMovie(Movie movie) {
        tvTitle.setText(movie.getTitle());
        tvDate.setText(movie.getDate());
        tvDescription.setText(movie.getDescription());
        imgDetail.setImageResource(movie.getPhoto());
    }
}
