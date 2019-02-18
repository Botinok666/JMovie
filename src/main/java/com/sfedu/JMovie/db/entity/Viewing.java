package com.sfedu.JMovie.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Viewing {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @NotNull
    private Movie movie;

    @Column(nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(nullable = false)
    @NotNull
    private float ratingUser;

    public Viewing(){}

    public Viewing(@NotNull LocalDate date, @NotNull float ratingUser){
        setDate(date);
        setRatingUser(ratingUser);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getRatingUser() {
        return ratingUser;
    }

    public void setRatingUser(float ratingUser) {
        this.ratingUser = ratingUser;
    }
}
