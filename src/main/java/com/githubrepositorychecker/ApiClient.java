package com.githubrepositorychecker;

import com.githubrepositorychecker.domain.Branch;
import com.githubrepositorychecker.domain.GitRepository;
import com.githubrepositorychecker.exception.UserNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

        try {
            GitRepository[] repositories = restTemplate.getForObject(url, GitRepository[].class);
            List<GitRepository> resultRepository =  Arrays.stream(repositories).toList()
                    .stream()
                    .filter(p -> !p.isFork())
                    .collect(Collectors.toList());
            for (GitRepository repository : resultRepository ) {
                List<Branch> branches = getBranchesForRepository(repository.getOwnerInfo().getOwnerLogin(), repository.getRepositoryName());
                repository.setBranches(branches);
            }
            return resultRepository;

        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        }
    }

    public List<Branch> getBranchesForRepository(String owner, String repositoryName) {
        String url = BASE_URL + "/repos/" + owner + "/" + repositoryName + "/branches";


        ResponseEntity<Branch[]> response = restTemplate.getForEntity(url, Branch[].class);

            return Arrays.asList(response.getBody());

    }
}