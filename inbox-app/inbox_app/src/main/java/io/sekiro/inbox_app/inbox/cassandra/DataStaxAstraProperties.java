package io.sekiro.inbox_app.inbox.cassandra;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

// expose datastax.astra secure-connect-bundle
@ConfigurationProperties(prefix = "datastax.astra" )
public class DataStaxAstraProperties {
    private File secureConnectBundle;

    public File getSecureConnectBundle() {
        return secureConnectBundle;
    }

    public void setSecureConnectBundle(File secureConnectBundle) {
        this.secureConnectBundle = secureConnectBundle;
    }
}
