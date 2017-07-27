package com.projeto.patyalves.projeto.model;

/**
 * Created by logonrm on 25/07/2017.
 */

public class User {

    private int id;

    public void setUser(String user) {
        this.user = user;
    }

    private String user;
    private String password;

    public User(String user, String password) {
        this.user=user;
        this.password=password;
    }
    public User(int id,String user, String password) {
        this.id=id;
        this.user=user;
        this.password=password;
    }

    public User() { }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }
}
