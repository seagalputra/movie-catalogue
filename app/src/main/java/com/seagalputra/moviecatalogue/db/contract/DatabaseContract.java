package com.seagalputra.moviecatalogue.db.contract;

public class DatabaseContract {
    public static String MOVIE_TABLE_NAME = "movies";
    public static String TVSHOW_TABLE_NAME = "tvshow";

    public static class DatabaseColumns {
        public static String ID = "id";
        public static String PHOTO = "photo";
        public static String TYPE = "type";
        public static String TITLE = "title";
        public static String DATE = "date";
        public static String DESCRIPTION = "description";
    }
}
