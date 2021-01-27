package org.cgi.assesment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.cgi.assesment.model.Recipes;
import org.cgi.assesment.service.LogsService;
import org.cgi.assesment.service.RecipesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Stream;

@Slf4j
@SpringBootApplication
public class CGIAssessment {

    public static void main(String[] args) {
        SpringApplication.run(CGIAssessment.class, args);
    }

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
        log.info("Spring boot application running in UTC timezone : {}", new Date());   // It will print UTC timezone
    }

    @Bean
    CommandLineRunner runner(RecipesService service, LogsService logsService){
        return args -> {
            //read json and write to h2 db
            ObjectMapper mapper = new ObjectMapper();
            //TypeReference<List<Recipes>> typeReference = ;
            try {
                InputStream inputStream = TypeReference.class.getResourceAsStream("/receipe.json");
                List<Recipes> recipes = mapper.readValue(inputStream, new TypeReference<List<Recipes>>() {});
                service.save(recipes);
                log.info("Saved Recipes to DB.");
            } catch (IOException ioe) {
                log.info("Unable to save the recipes:" + ioe.getMessage());
            }
            log.info("Reading File .... {}");
            //parse log file to save to h2 db
            try(Stream<String> stream = Files.lines(Paths.get("src/main/resources/logFile-2018-09-10.log"))) {
                stream.forEach(logsService::parse);
                log.info("Parsed log File ..... ");
            } catch (IOException ioe){
                log.info("Unable to read logs from" + ioe.getMessage());
            }

        };
    }

    @Bean
    public WebMvcConfigurer CorsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE");
            }
        };
    }

}
