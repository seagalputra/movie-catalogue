package com.seagalputra.moviecatalogue.presenter;

import android.content.res.TypedArray;

import com.seagalputra.moviecatalogue.model.Movie;
import com.seagalputra.moviecatalogue.view.MainView;

import java.util.ArrayList;

public class MainPresenter {
    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    public ArrayList<Movie> addMovieData(String[] moviesTitle, String[] moviesDate, String[] moviesDescription, TypedArray moviesPhoto) {
        ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i < moviesTitle.length; i++) {
            Movie movie = new Movie();
            movie.setPhoto(moviesPhoto.getResourceId(i, -1));
            movie.setTitle(moviesTitle[i]);
            movie.setDate(moviesDate[i]);
            movie.setDescription(moviesDescription[i]);
            movies.add(movie);
        }

        return movies;
    }

    public void showListData(ArrayList<Movie> movies) {
        mainView.showList(movies);
    }
}
