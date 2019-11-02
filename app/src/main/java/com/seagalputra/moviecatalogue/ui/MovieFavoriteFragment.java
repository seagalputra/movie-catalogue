package com.seagalputra.moviecatalogue.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.seagalputra.moviecatalogue.DetailActivity;
import com.seagalputra.moviecatalogue.R;
import com.seagalputra.moviecatalogue.adapter.ListMovieAdapter;
import com.seagalputra.moviecatalogue.entity.Movie;
import com.seagalputra.moviecatalogue.repository.Repository;
import com.seagalputra.moviecatalogue.repository.RepositoryImpl;
import com.seagalputra.moviecatalogue.service.LoadDataCallback;
import com.seagalputra.moviecatalogue.service.LoadMovieAsync;
import com.seagalputra.moviecatalogue.viewmodel.MovieViewModel;

import java.util.ArrayList;

import static com.seagalputra.moviecatalogue.DetailActivity.EXTRA_POSITION;

public class MovieFavoriteFragment extends Fragment implements LoadDataCallback {

    private ProgressBar progressBar;
    private Repository repository;
    private ListMovieAdapter listMovieAdapter;

    private MovieViewModel movieViewModel;

    public MovieFavoriteFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressbar_favorite_movie);
        repository = RepositoryImpl.getInstance(view.getContext());

        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);

        RecyclerView rvMovies = view.findViewById(R.id.rv_favorite_movie);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvMovies.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        listMovieAdapter = new ListMovieAdapter(getActivity());
        listMovieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMovieAdapter);

        new LoadMovieAsync(repository, this).execute();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movieViewModel.getListFavoriteMovie().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies != null) {
                    listMovieAdapter.setMovieData(movies);
                }
            }
        });
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoading(true);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        showLoading(false);

        if (movies.size() > 0) {
            movieViewModel.setListFavoriteMovie(movies);
        } else {
            movieViewModel.setListFavoriteMovie(new ArrayList<Movie>());
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
