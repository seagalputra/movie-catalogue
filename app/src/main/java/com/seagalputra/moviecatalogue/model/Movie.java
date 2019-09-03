package com.seagalputra.moviecatalogue.model;

public class Movie {
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
}
