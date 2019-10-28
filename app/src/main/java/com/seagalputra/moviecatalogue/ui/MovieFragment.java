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

import com.seagalputra.moviecatalogue.BuildConfig;
import com.seagalputra.moviecatalogue.DetailActivity;
import com.seagalputra.moviecatalogue.R;
import com.seagalputra.moviecatalogue.adapter.ListMovieAdapter;
import com.seagalputra.moviecatalogue.entity.Movie;
import com.seagalputra.moviecatalogue.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class MovieFragment extends Fragment {

    private ProgressBar progressBar;
    private ListMovieAdapter listMovieAdapter;
    private MovieViewModel movieViewModel;

    public MovieFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressbar_movie);

        String language = Locale.getDefault().getLanguage();
        movieViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);
        if (language.equals("in")) {
            movieViewModel.setMovie("id");
        } else {
            movieViewModel.setMovie(language);
        }

        showLoading(true);

        RecyclerView rvMovies = view.findViewById(R.id.rv_movies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvMovies.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        listMovieAdapter = new ListMovieAdapter();
        listMovieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMovieAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movieViewModel.getMovies().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies != null) {
                    listMovieAdapter.setMovieData(movies);
                    showLoading(false);

                    listMovieAdapter.setOnItemClickCallback(new ListMovieAdapter.OnItemClickCallback() {
                        @Override
                        public void onItemClicked(Movie movie) {
                            navigateToDetail(movie);
                        }
                    });
                }
            }
        });
    }

    private void navigateToDetail(Movie movie) {
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        detailIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(detailIntent);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
