package com.sfedu.JMovie.domain.model;

public class PersonDomain {
    private Integer id;
    private String name;
    public PersonDomain(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
