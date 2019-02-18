package com.sfedu.JMovie.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Short id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String pwd;

    @OneToMany(mappedBy = "user")
    private List<Viewing> viewings = new ArrayList<>();

    public User(){}

    public User(@NotNull String name, @NotNull String pwd){
        setName(name);
        setPwd(pwd);
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<Viewing> getViewings() {
        return viewings;
    }

    public void setViewings(List<Viewing> viewings) {
        this.viewings = viewings;
    }
}
