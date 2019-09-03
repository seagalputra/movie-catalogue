package com.seagalputra.moviecatalogue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seagalputra.moviecatalogue.R;
import com.seagalputra.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Movie> movies;
    private Movie movie;

    public MovieAdapter(Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        movie = (Movie) getItem(i);
        viewHolder.bind(movie);
        return view;
    }

    private class ViewHolder {
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvDescription;
        private ImageView imgPhoto;

        private ViewHolder(View view) {
            this.tvTitle = view.findViewById(R.id.tv_title);
            this.tvDate = view.findViewById(R.id.tv_date);
            this.tvDescription = view.findViewById(R.id.tv_description);
            this.imgPhoto = view.findViewById(R.id.img_photo);
        }

        private void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvDate.setText(movie.getDate());
            tvDescription.setText(movie.getDescription());
            imgPhoto.setImageResource(movie.getPhoto());
        }
    }
}
