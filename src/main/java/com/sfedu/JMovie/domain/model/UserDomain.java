package com.sfedu.JMovie.domain.model;

public class UserDomain {
    private Short id;
    private String name;
    private String pwd;
    public UserDomain(Short id, String name, String pwd){
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public Short getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }
}
