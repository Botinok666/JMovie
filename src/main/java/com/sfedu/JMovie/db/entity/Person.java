package com.sfedu.JMovie.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class Person {
    @Id
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "director")
    private Set<Movie> moviesDirector = new HashSet<>();

    @OneToMany(mappedBy = "screenwriter")
    private Set<Movie> moviesScreenwriter = new HashSet<>();

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> moviesActor = new HashSet<>();

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

    public Set<Movie> getMoviesDirector() {
        return moviesDirector;
    }

    public void setMoviesDirector(HashSet<Movie> moviesDirector) {
        this.moviesDirector = moviesDirector;
    }

    public Set<Movie> getMoviesScreenwriter() {
        return moviesScreenwriter;
    }

    public void setMoviesScreenwriter(HashSet<Movie> moviesScreenwriter) {
        this.moviesScreenwriter = moviesScreenwriter;
    }

    public Set<Movie> getMoviesActor() {
        return moviesActor;
    }

    public void setMoviesActor(HashSet<Movie> moviesActor) {
        this.moviesActor = moviesActor;
    }
}
