package com.sfedu.JMovie.api.data;

public class MovieData {
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
}
