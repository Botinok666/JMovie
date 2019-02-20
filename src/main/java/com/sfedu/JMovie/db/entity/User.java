package com.sfedu.JMovie.db.entity;

import com.sfedu.JMovie.db.RoleType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private RoleType role;

    public User(){}

    public User(@NotNull String name, @NotNull String pwd, @NotNull RoleType role){
        setName(name);
        setPwd(pwd);
        setRole(role);
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

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
