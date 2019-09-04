package com.seagalputra.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int photo;
    private String title;
    private String date;
    private String description;

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

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Movie() {
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
