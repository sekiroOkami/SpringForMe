package io.sekiro.inbox_app.inbox.cassandra;

import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Path;

@Configuration
public class DatastaxBundle {
    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) throws IOException {
        Path bundle = astraProperties.getSecureConnectBundlePath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }
}
