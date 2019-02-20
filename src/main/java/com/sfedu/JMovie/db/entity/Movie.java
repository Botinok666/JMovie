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

    @Column(nullable = false)
    @NotNull
    private String originalTitle;

    @Column(nullable = false)
    @NotNull
    private String posterLink;

    @Column(nullable = false)
    @NotNull
    private short year;

    @ManyToMany
    @JoinTable
    private List<Country> countries = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Person director;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Person screenwriter;

    @Column(nullable = false)
    @NotNull
    private String tagLine;

    @ManyToMany
    @JoinTable
    private List<Genre> genres = new ArrayList<>();

    @Column(nullable = false)
    @NotNull
    private short runtime;

    @ManyToMany
    @JoinTable
    private List<Person> actors = new ArrayList<>();

    @Column(nullable = false)
    @NotNull
    @Lob
    private String storyline;

    @Column(nullable = false)
    @NotNull
    private float ratingKP;

    @Column(nullable = false)
    @NotNull
    private Float ratingIMDB;

    @OneToMany(mappedBy = "movie")
    private List<Viewing> viewings = new ArrayList<>();

    public Movie(){}

    public Movie(@NotNull Integer id, @NotNull String localizedTitle, @NotNull String originalTitle,
                 @NotNull String posterLink, @NotNull short year, @NotNull String tagLine, @NotNull short runtime,
                 @NotNull String storyline, @NotNull float ratingKP, @NotNull Float ratingIMDB){
        setId(id);
        setLocalizedTitle(localizedTitle);
        setOriginalTitle(originalTitle);
        setPosterLink(posterLink);
        setYear(year);
        setTagLine(tagLine);
        setRuntime(runtime);
        setStoryline(storyline);
        setRatingKP(ratingKP);
        setRatingIMDB(ratingIMDB);
    }

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

    public void addCountry(Country country){
        getCountries().add(country);
        country.getMovies().add(this);
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
        director.getMoviesDirector().add(this);
    }

    public Person getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(Person screenwriter) {
        this.screenwriter = screenwriter;
        screenwriter.getMoviesScreenwriter().add(this);
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

    public void addGenre(Genre genre){
        getGenres().add(genre);
        genre.getMovies().add(this);
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

    public void addActor(Person actor){
        getActors().add(actor);
        actor.getMoviesActor().add(this);
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

    public Float getRatingIMDB() {
        return ratingIMDB;
    }

    public void setRatingIMDB(Float ratingIMDB) {
        this.ratingIMDB = ratingIMDB;
    }

    public List<Viewing> getViewings() {
        return viewings;
    }

    public void setViewings(List<Viewing> viewings) {
        this.viewings = viewings;
    }
}
