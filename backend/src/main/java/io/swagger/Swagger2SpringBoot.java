package io.swagger;

import core.DatabaseCommunicator;
import io.swagger.configuration.LocalDateConverter;
import io.swagger.configuration.LocalDateTimeConverter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.oas.annotations.EnableOpenApi;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import utility.PackagedFileReader;

import java.io.File;
import java.net.URL;

import static utility.Loader.getWorkingCanonicalDirectory;
import static utility.PackagedFileReader.readPackagedFile;

@Slf4j
@SpringBootApplication
@EnableOpenApi
@ComponentScan(basePackages = { "io.swagger", "io.swagger.api" , "io.swagger.configuration"})
public class Swagger2SpringBoot implements CommandLineRunner {

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) throws Exception {
        //mvn clean package spring-boot:repackage
        DatabaseCommunicator dbc = new DatabaseCommunicator("SQLiteDataSource");
        dbc.createConnection();
        log.info("Database at " + dbc.getDbUrl());
        //dbc.instantiateDatabaseV1("src/main/resources/artists.csv");
        //dbc.instantiateDatabaseV1("dsrc/main/resources/tracks.csv");
        dbc.instantiateDatabaseV3(readPackagedFile("tracks.csv"));
        dbc.instantiateDatabaseV3(readPackagedFile("artists.csv"));
        dbc.closeConnection();
        new SpringApplication(Swagger2SpringBoot.class).run(args);
    }

    @Configuration
    static class CustomDateConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
            registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        }
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
