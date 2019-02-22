package com.sfedu.JMovie.domain.model;

import java.util.ArrayList;
import java.util.List;

public class MovieDomain {
    private Integer id;
    private String localizedTitle;
    private String originalTitle;
    private String posterLink;
    private short year;
    private List<CountryDomain> countries = new ArrayList<>();
    private PersonDomain director;
    private PersonDomain screenwriter;
    private String tagLine;
    private List<GenreDomain> genres = new ArrayList<>();
    private short runtime;
    private List<PersonDomain> actors = new ArrayList<>();
    private String storyline;
    private float ratingKP;
    private float ratingIMDB;
    public MovieDomain(Integer id, String localizedTitle, String originalTitle,
                       String posterLink, short year, String tagLine, short runtime,
                       String storyline, float ratingKP, float ratingIMDB){
        this.id = id;
        this.localizedTitle = localizedTitle;
        this.originalTitle = originalTitle;
        this.posterLink = posterLink;
        this.year = year;
        this.tagLine = tagLine;
        this.runtime = runtime;
        this.storyline = storyline;
        this.ratingKP = ratingKP;
        this.ratingIMDB = ratingIMDB;
    }

    public Integer getId() {
        return id;
    }

    public String getLocalizedTitle() {
        return localizedTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public short getYear() {
        return year;
    }

    public String getTagLine() {
        return tagLine;
    }

    public short getRuntime() {
        return runtime;
    }

    public String getStoryline() {
        return storyline;
    }

    public float getRatingKP() {
        return ratingKP;
    }

    public float getRatingIMDB() {
        return ratingIMDB;
    }

    public List<CountryDomain> getCountries() {
        return countries;
    }

    public void addCountry(CountryDomain country) {
        this.getCountries().add(country);
    }

    public List<GenreDomain> getGenres() {
        return genres;
    }

    public void addGenre(GenreDomain genre) {
        this.getGenres().add(genre);
    }

    public List<PersonDomain> getActors() {
        return actors;
    }

    public void addActor(PersonDomain actor) {
        this.getActors().add(actor);
    }

    public PersonDomain getDirector() {
        return director;
    }

    public void setDirector(PersonDomain director) {
        this.director = director;
    }

    public PersonDomain getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(PersonDomain screenwriter) {
        this.screenwriter = screenwriter;
    }
}
