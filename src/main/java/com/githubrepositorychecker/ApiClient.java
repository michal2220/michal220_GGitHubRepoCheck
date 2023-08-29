package com.githubrepositorychecker;

import com.githubrepositorychecker.domain.Branch;
import com.githubrepositorychecker.domain.GitRepository;
import com.githubrepositorychecker.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static String GIT_URL = "https://api.github.com";
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);


    public List<GitRepository> fetchRepository(String username) throws UserNotFoundException {
        String url = GIT_URL + "/users/" + username + "/repos";

        try {
            GitRepository[] repositories = restTemplate.getForObject(url, GitRepository[].class);
            LOGGER.info("Response from API received");

            List<GitRepository> resultRepository = Arrays.stream(repositories).toList()
                    .stream()
                    .filter(p -> !p.isFork())
                    .collect(Collectors.toList());

            for (GitRepository repository : resultRepository) {
                List<Branch> branches = getBranchesForRepository(repository.getOwnerInfo().getOwnerLogin(), repository.getRepositoryName());
                repository.setBranches(branches);
            }
            return resultRepository;

        } catch (HttpClientErrorException.NotFound e) {
            LOGGER.warn("No user was found");
            throw new UserNotFoundException();
        }
    }

    private List<Branch> getBranchesForRepository(String owner, String repositoryName) {
        String url = GIT_URL + "/repos/" + owner + "/" + repositoryName + "/branches";

        Branch[] response = restTemplate.getForObject(url, Branch[].class);
        LOGGER.info("Response from API-BRANCHES received");
        return Arrays.asList(response);
    }
}