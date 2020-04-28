package com.instagram.download.photo.parameters;

public class UserParameters {
    private final String username;
    private final String password;
    private final String link;

    public UserParameters(String username, String password, String link) {
        this.username = username;
        this.password = password;
        this.link = link;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLink() {
        return link;
    }

}
