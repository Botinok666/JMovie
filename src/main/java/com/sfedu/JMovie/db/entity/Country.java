package com.sfedu.JMovie.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Country {
    @Id
    private Short id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @ManyToMany
    @JoinTable(name = "movie-country")
    private List<Movie> movies = new ArrayList<>();

    public Country(){}

    public Country(@NotNull Short id, @NotNull String name){
        setId(id);
        setName(name);
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
