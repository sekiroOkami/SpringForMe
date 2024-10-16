package io.sekiro.inbox_app;

import io.sekiro.inbox_app.cassandra.DataStaxAstraProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;

@SpringBootApplication
public class InboxAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(InboxAppApplication.class, args);
	}
}
