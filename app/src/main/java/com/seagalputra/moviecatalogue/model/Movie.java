package com.seagalputra.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class Movie implements Parcelable {
    private final int photo;
    private final String title;
    private final String date;
    private final String description;

    public Movie(MovieBuilder movieBuilder) {
        this.photo = movieBuilder.photo;
        this.title = movieBuilder.title;
        this.date = movieBuilder.date;
        this.description = movieBuilder.description;
    }

    public String getTitle() {
        return title;
    }

    public int getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public static class MovieBuilder {
        private int photo;
        private final String title;
        private String date;
        private String description;

        public MovieBuilder(String title) {
            if (title == null) {
                throw new IllegalArgumentException("title can not be null");
            }

            this.title = title;
        }

        public MovieBuilder withPhoto(int photo) {
            this.photo = photo;
            return this;
        }

        public MovieBuilder withDate(String date) {
            this.date = date;
            return this;
        }

        public MovieBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.photo);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.description);
    }

    protected Movie(Parcel in) {
        this.photo = in.readInt();
        this.title = in.readString();
        this.date = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
