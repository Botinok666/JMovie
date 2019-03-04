package com.sfedu.JMovie.domain;

public enum GetOptions {
    GetAll("<нет>"),
    GetByActor("актёру"),
    GetByCountry("стране"),
    GetByDirector("режиссёру"),
    GetByGenre("жанру"),
    GetByStoryline("сюжету"),
    GetByTitle("названию"),
    GetByUser("просмотрам"),
    GetByYearPeriod("году");

    String name;
    GetOptions(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }
}
