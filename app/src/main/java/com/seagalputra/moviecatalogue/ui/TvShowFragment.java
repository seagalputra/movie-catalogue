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

public class TvShowFragment extends Fragment implements MainView, Data {

    private RecyclerView rvTvShows;

    private ArrayList<Movie> tvShows;
    private String[] tvShowsTitle;
    private String[] tvShowsDate;
    private String[] tvShowsDescription;
    private TypedArray tvShowsPhoto;

    public TvShowFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvShows = view.findViewById(R.id.rv_tv_shows);
        rvTvShows.setHasFixedSize(true);
        rvTvShows.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvTvShows.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        prepareData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MainPresenter presenter = new MainPresenter(this);
        tvShows = presenter.addMovieData(tvShowsTitle, tvShowsDate, tvShowsDescription, tvShowsPhoto);
        presenter.showListData(tvShows);
    }

    @Override
    public void prepareData() {
        tvShowsTitle = getResources().getStringArray(R.array.tv_shows_title);
        tvShowsDate = getResources().getStringArray(R.array.tv_shows_date);
        tvShowsDescription = getResources().getStringArray(R.array.tv_shows_description);
        tvShowsPhoto = getResources().obtainTypedArray(R.array.tv_shows_poster);
    }

    @Override
    public void showList(ArrayList<Movie> list) {
        ListMovieAdapter listTvShowAdapter = new ListMovieAdapter(list);
        rvTvShows.setAdapter(listTvShowAdapter);

        listTvShowAdapter.setOnItemClickCallback(new ListMovieAdapter.OnItemClickCallback() {
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
}
