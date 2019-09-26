package com.seagalputra.moviecatalogue.view;

import com.seagalputra.moviecatalogue.model.Movie;

import java.util.ArrayList;

public interface MainView {
    <T> void showList(ArrayList<T> list);
    void navigateToDetail(Movie movie);
}
