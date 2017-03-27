package com.capps.question.Login;

/**
 * Created by varun on 27/3/17.
 */

public class User {

    private String name,email;
    private int id;


    public User(Boolean admin, String email, String name) {
        this.admin = admin;
        this.email = email;
        this.name = name;
    }

    public User( String email, String name) {
        this.admin = false;
        this.email = email;
        this.name = name;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    private Boolean admin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
