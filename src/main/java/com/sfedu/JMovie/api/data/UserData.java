package com.sfedu.JMovie.api.data;

import com.sfedu.JMovie.db.RoleType;

public class UserData {
    private Short id;
    private String name;
    private String pwd;
    private RoleType role;
    public UserData(Short id, String name, String pwd, RoleType role){
        this.setId(id);
        this.setName(name);
        this.setPwd(pwd);
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

    public void setId(Short id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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
