package com.sfedu.JMovie.domain.model;

import java.time.LocalDate;

public class ViewingDomain {
    private Integer id;
    private Short user_id;
    private LocalDate date;
    private Integer movie_id;
    private float ratingUser;
    public ViewingDomain(Integer id, Short user_id, LocalDate date,
                         Integer movie_id, float ratingUser){
        this.id = id;
        this.user_id = user_id;
        this.date = date;
        this.movie_id = movie_id;
        this.ratingUser = ratingUser;
    }

    public Integer getId() {
        return id;
    }

    public Short getUser_id() {
        return user_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public float getRatingUser() {
        return ratingUser;
    }
}
