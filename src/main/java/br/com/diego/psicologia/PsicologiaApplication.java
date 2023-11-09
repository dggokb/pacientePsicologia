package br.com.diego.psicologia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PsicologiaApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(PsicologiaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PsicologiaApplication.class);
    }
}
