package com.seagalputra.moviecatalogue.view;

import com.seagalputra.moviecatalogue.model.Movie;

import java.util.ArrayList;

public interface MainView {
    void showList(ArrayList<Movie> list);
    void navigateToDetail(Movie movie);
}
