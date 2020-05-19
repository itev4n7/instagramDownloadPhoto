package com.instagram.download.photo.parameters;

public class UserParameters {
    private final String username;
    private final String password;
    private final String link;

    private UserParameters(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.link = builder.link;
    }

    public static class Builder {
        private String username;
        private String password;
        private String link;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setLink(String link) {
            this.link = link;
            return this;
        }

        public UserParameters build() {
            return new UserParameters(this);
        }
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
