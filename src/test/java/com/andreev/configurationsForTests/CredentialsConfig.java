package com.andreev.configurationsForTests;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/credential.properties"})
public interface CredentialsConfig extends Config {
    String login();
    String password();
}
