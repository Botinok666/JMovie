package com.sfedu.JMovie.api.data;

public class UserData {
    private Short id;
    private String name;
    private String pwd;
    public UserData(Short id, String name, String pwd){
        this.setId(id);
        this.setName(name);
        this.setPwd(pwd);
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
}
