package com.sfedu.JMovie.api.data;

import java.time.LocalDate;

public class ViewingData {
    private Integer id;
    private LocalDate date;
    private float ratingUser;
    public ViewingData(Integer id, LocalDate date, float ratingUser){
        this.setId(id);
        this.setDate(date);
        this.setRatingUser(ratingUser);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getRatingUser() {
        return ratingUser;
    }

    public void setRatingUser(float ratingUser) {
        this.ratingUser = ratingUser;
    }
}
