package com.sfedu.JMovie.api.data;

public class GenreData {
    private Short id;
    private String name;
    public GenreData(Short id, String name){
        this.setId(id);
        this.setName(name);
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
