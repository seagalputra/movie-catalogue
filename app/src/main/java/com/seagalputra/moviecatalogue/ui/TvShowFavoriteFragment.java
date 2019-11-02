package com.seagalputra.moviecatalogue.ui;


import android.content.Intent;
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
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.seagalputra.moviecatalogue.DetailActivity;
import com.seagalputra.moviecatalogue.R;
import com.seagalputra.moviecatalogue.adapter.ListMovieAdapter;
import com.seagalputra.moviecatalogue.entity.Movie;
import com.seagalputra.moviecatalogue.repository.Repository;
import com.seagalputra.moviecatalogue.repository.RepositoryImpl;
import com.seagalputra.moviecatalogue.service.LoadDataCallback;
import com.seagalputra.moviecatalogue.service.LoadTvShowAsync;

import java.util.ArrayList;

public class TvShowFavoriteFragment extends Fragment implements LoadDataCallback {

    private ProgressBar progressBar;
    private Repository repository;
    private ListMovieAdapter movieAdapter;

    public TvShowFavoriteFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressbar_favorite_tvshow);
        repository = RepositoryImpl.getInstance(view.getContext());

        RecyclerView rvTvShow = view.findViewById(R.id.rv_favorite_tvshow);
        rvTvShow.setHasFixedSize(true);
        rvTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvTvShow.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        movieAdapter = new ListMovieAdapter();
        movieAdapter.notifyDataSetChanged();
        rvTvShow.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickCallback(new ListMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                navigateToDetail(movie);
            }
        });

        new LoadTvShowAsync(repository, this).execute();
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movies.size() > 0) {
            movieAdapter.setMovieData(movies);
        } else {
            movieAdapter.setMovieData(new ArrayList<Movie>());
        }
    }

    private void navigateToDetail(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}
