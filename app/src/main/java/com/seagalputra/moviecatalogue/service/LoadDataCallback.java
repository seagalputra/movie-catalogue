package com.seagalputra.moviecatalogue.service;

import com.seagalputra.moviecatalogue.entity.Movie;

import java.util.ArrayList;

public interface LoadDataCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}
