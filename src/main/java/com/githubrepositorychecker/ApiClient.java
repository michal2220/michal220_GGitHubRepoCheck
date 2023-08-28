package com.githubrepositorychecker;

import com.githubrepositorychecker.domain.GitRepository;
import com.githubrepositorychecker.exception.UserNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "https://api.github.com";


    public List<GitRepository> fetchRepository(String username) throws UserNotFoundException {
        String url = BASE_URL + "/users/" + username + "/repos";

/*        HttpHeaders header = new HttpHeaders();
        header.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(header);

        ParameterizedTypeReference<List<GitRepository>> responseType = new ParameterizedTypeReference<>() {
        };
        */


        try {
            GitRepository[] repositories = restTemplate.getForObject(url, GitRepository[].class);
            return Arrays.stream(repositories).toList()
                    .stream()
                    .filter(p -> !p.isFork())
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        }
    }
}