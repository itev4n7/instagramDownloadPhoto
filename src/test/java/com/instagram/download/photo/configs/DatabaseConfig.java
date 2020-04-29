package com.instagram.download.photo.configs;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.Sources;

@Sources("file:src/test/resources/DatabaseConfig.properties")
public interface DatabaseConfig extends Config {
    @Key("database.url")
    String url();

    @Key("database.username")
    String username();

    @Key("database.password")
    String password();
}
