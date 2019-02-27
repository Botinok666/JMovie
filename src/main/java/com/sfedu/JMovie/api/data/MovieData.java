package com.sfedu.JMovie.api.data;

import java.util.ArrayList;
import java.util.List;

public class MovieData {
    private Integer id;
    private String localizedTitle;
    private String originalTitle;
    private String posterLink;
    private short year;
    private List<CountryData> countries = new ArrayList<>();
    private PersonData director;
    private PersonData screenwriter;
    private String tagLine;
    private List<GenreData> genres = new ArrayList<>();
    private short runtime;
    private List<PersonData> actors = new ArrayList<>();
    private String storyline;
    private float ratingKP;
    private float ratingIMDB;
    public MovieData(){}
    public MovieData(Integer id, String localizedTitle, String originalTitle,
                       String posterLink, short year, String tagLine, short runtime,
                       String storyline, float ratingKP, float ratingIMDB){
        this.setId(id);
        this.setLocalizedTitle(localizedTitle);
        this.setOriginalTitle(originalTitle);
        this.setPosterLink(posterLink);
        this.setYear(year);
        this.setTagLine(tagLine);
        this.setRuntime(runtime);
        this.setStoryline(storyline);
        this.setRatingKP(ratingKP);
        this.setRatingIMDB(ratingIMDB);
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

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public short getRuntime() {
        return runtime;
    }

    public void setRuntime(short runtime) {
        this.runtime = runtime;
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

    public List<CountryData> getCountries() {
        return countries;
    }

    public void addCountry(CountryData country) {
        this.getCountries().add(country);
    }

    public List<GenreData> getGenres() {
        return genres;
    }

    public void addGenre(GenreData genre) {
        this.getGenres().add(genre);
    }

    public List<PersonData> getActors() {
        return actors;
    }

    public void addActor(PersonData actor) {
        this.getActors().add(actor);
    }

    public PersonData getDirector() {
        return director;
    }

    public void setDirector(PersonData director) {
        this.director = director;
    }

    public PersonData getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(PersonData screenwriter) {
        this.screenwriter = screenwriter;
    }
}
