package com.seagalputra.moviecatalogue.presenter;

import com.seagalputra.moviecatalogue.model.Movie;
import com.seagalputra.moviecatalogue.view.MovieView;

public class DetailPresenter {
    private MovieView movieView;

    public DetailPresenter(MovieView movieView) {
        this.movieView = movieView;
    }

    public void showDetailMovie(Movie movie) {
        movieView.viewDetailMovie(movie);
    }
}
