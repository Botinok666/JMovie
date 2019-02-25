package com.sfedu.JMovie.domain.model;

import com.sfedu.JMovie.db.RoleType;

public class UserDomain {
    private Short id;
    private String name;
    private String pwd;
    private RoleType role;
    public UserDomain(Short id, String name, String pwd, RoleType role){
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        setRole(role);
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

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
