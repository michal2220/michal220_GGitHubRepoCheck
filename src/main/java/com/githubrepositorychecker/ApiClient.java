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

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "https://api.github.com";


    public List<GitRepository> fetchRepository(String username) throws UserNotFoundException {
        String url = BASE_URL + "/users/" + username + "/repos";

        HttpHeaders header = new HttpHeaders();
        header.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(header);

        ParameterizedTypeReference<List<GitRepository>> responseType = new ParameterizedTypeReference<>() {
        };

        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, responseType).getBody()
                    .stream()
                    .filter(p -> !p.isFork())
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        }
    }
}

/*return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(Map.of("status", HttpStatus.NOT_ACCEPTABLE.value(), "message", "Invalid accept header."));*/



        /*Optional.ofNullable(response).map(Arrays::asList).orElse(Collections.emptyList())
                .stream()
                .filter(p->!p.isFork())
                .collect(Collectors.toList());*/
