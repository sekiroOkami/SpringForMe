package io.sekiro.inbox_app.inbox.cassandra;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

// expose datastax.astra secure-connect-bundle
@Configuration
@ConfigurationProperties(prefix = "datastax.astra" )
public class DataStaxAstraProperties {
    private Resource secureConnectBundle;

    public Resource getSecureConnectBundle() {
        return secureConnectBundle;
    }

    public Path getSecureConnectBundlePath() throws IOException {
        return secureConnectBundle.getFile().toPath();
    }
    public void setSecureConnectBundle(Resource secureConnectBundle) {
        this.secureConnectBundle = secureConnectBundle;
    }
}
