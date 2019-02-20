package com.sfedu.JMovie.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
public class Person {
    @Id
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private String name;

    public Person(){}

    public Person(@NotNull Integer id, @NotNull String name){
        setId(id);
        setName(name);
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
