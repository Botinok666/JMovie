package com.sfedu.JMovie.domain.model;

public class CountryDomain {
    private Short id;
    private String name;
    public CountryDomain(Short id, String name){
        this.id = id;
        this.name = name;
    }

    public Short getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
