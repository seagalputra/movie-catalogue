package com.seagalputra.moviecatalogue.presenter;

import com.seagalputra.moviecatalogue.model.Movie;
import com.seagalputra.moviecatalogue.view.DetailView;

public class DetailPresenter {
    private DetailView detailView;

    public DetailPresenter(DetailView detailView) {
        this.detailView = detailView;
    }

    public void showDetailMovie(Movie movie) {
        detailView.viewDetailMovie(movie);
    }
}
