package com.sfedu.JMovie.domain.model;

public class MovieDomain {
    private Integer id;
    private String localizedTitle;
    private String originalTitle;
    private String posterLink;
    private short year;
    private String tagLine;
    private short runtime;
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
}
