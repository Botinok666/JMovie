package com.sfedu.JMovie.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    @Id
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "director")
    private List<Movie> moviesDirector = new ArrayList<>();

    @OneToMany(mappedBy = "screenwriter")
    private List<Movie> moviesScreenwriter = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "movie-actor")
    private List<Movie> moviesActor = new ArrayList<>();

    public Person(){}

    public Person(@NotNull Integer id, @NotNull String name){
        setId(id);
        setName(name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMoviesDirector() {
        return moviesDirector;
    }

    public void setMoviesDirector(List<Movie> moviesDirector) {
        this.moviesDirector = moviesDirector;
    }

    public List<Movie> getMoviesScreenwriter() {
        return moviesScreenwriter;
    }

    public void setMoviesScreenwriter(List<Movie> moviesScreenwriter) {
        this.moviesScreenwriter = moviesScreenwriter;
    }

    public List<Movie> getMoviesActor() {
        return moviesActor;
    }

    public void setMoviesActor(List<Movie> moviesActor) {
        this.moviesActor = moviesActor;
    }
}
