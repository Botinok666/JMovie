package com.sfedu.JMovie.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private String localizedTitle;

    private String originalTitle;

    private String posterLink;

    @Column(nullable = false)
    @NotNull
    private short year;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie-country")
    private List<Country> countries = new ArrayList<>();

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "id")
    @NotNull
    private Person director;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "id")
    @NotNull
    private Person screenwriter;

    private String tagLine;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie-genre")
    private List<Genre> genres = new ArrayList<>();

    @Column(nullable = false)
    @NotNull
    private short runtime;

    @ManyToMany
    @JoinTable(name = "movie-actor")
    private List<Person> actors = new ArrayList<>();

    private String storyline;

    @Column(nullable = false)
    @NotNull
    private float ratingKP;

    private float ratingIMDB;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
    private List<Viewing> viewings = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocalizedTitle() {
        return localizedTitle;
    }

    public void setLocalizedTitle(String localizedTitle) {
        this.localizedTitle = localizedTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public Person getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(Person screenwriter) {
        this.screenwriter = screenwriter;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public short getRuntime() {
        return runtime;
    }

    public void setRuntime(short runtime) {
        this.runtime = runtime;
    }

    public List<Person> getActors() {
        return actors;
    }

    public void setActors(List<Person> actors) {
        this.actors = actors;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public float getRatingKP() {
        return ratingKP;
    }

    public void setRatingKP(float ratingKP) {
        this.ratingKP = ratingKP;
    }

    public float getRatingIMDB() {
        return ratingIMDB;
    }

    public void setRatingIMDB(float ratingIMDB) {
        this.ratingIMDB = ratingIMDB;
    }

    public List<Viewing> getViewings() {
        return viewings;
    }

    public void setViewings(List<Viewing> viewings) {
        this.viewings = viewings;
    }
}
