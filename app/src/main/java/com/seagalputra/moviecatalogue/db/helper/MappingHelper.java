package com.seagalputra.moviecatalogue.db.helper;

import android.database.Cursor;

import com.seagalputra.moviecatalogue.db.contract.DatabaseContract;
import com.seagalputra.moviecatalogue.entity.Movie;
import com.seagalputra.moviecatalogue.entity.MovieBuilder;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<Movie> moviesList = new ArrayList<>();

        while (movieCursor.moveToNext()) {
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.DatabaseColumns.ID));
            int type = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.DatabaseColumns.TYPE));
            String photo = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.DatabaseColumns.PHOTO));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.DatabaseColumns.TITLE));
            String date = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.DatabaseColumns.DATE));
            String description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.DatabaseColumns.DESCRIPTION));
            int favorite = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.DatabaseColumns.FAVORITE));
            Movie movie = new MovieBuilder(id, title)
                    .withPhoto(photo)
                    .withDate(date)
                    .withDescription(description)
                    .withType(type)
                    .withFavorite(favorite)
                    .build();
            moviesList.add(movie);
        }

        return moviesList;
    }
}
