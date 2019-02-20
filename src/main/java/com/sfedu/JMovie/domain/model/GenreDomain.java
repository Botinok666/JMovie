package com.sfedu.JMovie.domain.model;

public class GenreDomain {
    private Short id;
    private String name;
    public GenreDomain(Short id, String name){
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
