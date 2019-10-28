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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment implements LoadDataCallback {

    private ProgressBar progressBar;
    private Repository repository;
    private ListMovieAdapter listMovieAdapter;

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

        RecyclerView rvMovies = view.findViewById(R.id.rv_favorite_movie);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvMovies.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        listMovieAdapter = new ListMovieAdapter();
        listMovieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMovieAdapter);

        listMovieAdapter.setOnItemClickCallback(new ListMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                navigateToDetail(movie);
            }
        });

        new LoadMovieAsync(repository, this).execute();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
            listMovieAdapter.setMovieData(movies);
        } else {
            listMovieAdapter.setMovieData(new ArrayList<Movie>());
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToDetail(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }
}
