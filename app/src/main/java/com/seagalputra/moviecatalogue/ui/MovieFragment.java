package com.seagalputra.moviecatalogue.ui;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seagalputra.moviecatalogue.DetailActivity;
import com.seagalputra.moviecatalogue.R;
import com.seagalputra.moviecatalogue.adapter.ListMovieAdapter;
import com.seagalputra.moviecatalogue.model.Data;
import com.seagalputra.moviecatalogue.model.Movie;
import com.seagalputra.moviecatalogue.presenter.MainPresenter;
import com.seagalputra.moviecatalogue.view.MainView;

import java.util.ArrayList;

public class MovieFragment extends Fragment implements MainView, Data {

    private RecyclerView rvMovies;

    private ArrayList<Movie> movies;
    private String[] moviesTitle;
    private String[] moviesDate;
    private String[] moviesDescription;
    private TypedArray moviesPhoto;

    public MovieFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvMovies.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        prepareData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MainPresenter presenter = new MainPresenter(this);
        movies = presenter.addMovieData(moviesTitle, moviesDate, moviesDescription, moviesPhoto);
        presenter.showListData(movies);
    }


    @Override
    public <T> void showList(ArrayList<T> list) {
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter((ArrayList<Movie>) list);
        rvMovies.setAdapter(listMovieAdapter);

        listMovieAdapter.setOnItemClickCallback(new ListMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                navigateToDetail(movie);
            }
        });
    }

    @Override
    public void navigateToDetail(Movie movie) {
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(detailIntent);
    }

    @Override
    public void prepareData() {
        moviesTitle = getResources().getStringArray(R.array.movies_title);
        moviesDate = getResources().getStringArray(R.array.movies_date);
        moviesDescription = getResources().getStringArray(R.array.movies_description);
        moviesPhoto = getResources().obtainTypedArray(R.array.movies_photo);
    }
}
