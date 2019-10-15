package com.seagalputra.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public final class Movie implements Parcelable {
    private final int id;
    private final String photo;
    private final String title;
    private final String date;
    private final String description;

    public Movie(MovieBuilder movieBuilder) {
        this.id = movieBuilder.id;
        this.photo = movieBuilder.photo;
        this.title = movieBuilder.title;
        this.date = movieBuilder.date;
        this.description = movieBuilder.description;
    }

    public String getTitle() {
        return title;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public static class MovieBuilder {
        private final int id;
        private String photo;
        private final String title;
        private String date;
        private String description;

        public MovieBuilder(int id, String title) {
            if (title == null) {
                throw new IllegalArgumentException("title can not be null");
            }

            this.id = id;
            this.title = title;
        }

        public MovieBuilder withPhoto(String photo) {
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
        dest.writeInt(this.id);
        dest.writeString(this.photo);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.description);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.photo = in.readString();
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
