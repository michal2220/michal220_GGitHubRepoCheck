package com.githubrepositorychecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

@SpringBootApplication
public class GitHubRepositoryCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitHubRepositoryCheckerApplication.class, args);
    }

    @Bean
    public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter() {
        return new MappingJackson2XmlHttpMessageConverter();
    }
}
