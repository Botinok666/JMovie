package com.sfedu.JMovie.domain.model;

import java.time.LocalDate;

public class ViewingDomain {
    private Integer id;
    private LocalDate date;
    private float ratingUser;
    public ViewingDomain(Integer id, LocalDate date, float ratingUser){
        this.id = id;
        this.date = date;
        this.ratingUser = ratingUser;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getRatingUser() {
        return ratingUser;
    }
}
