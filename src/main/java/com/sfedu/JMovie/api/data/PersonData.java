package com.sfedu.JMovie.api.data;

public class PersonData {
    private Integer id;
    private String name;
    public PersonData(Integer id, String name){
        this.setId(id);
        this.setName(name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
