package com.githubrepositorychecker;


import com.githubrepositorychecker.domain.GitRepository;
import com.githubrepositorychecker.exception.ErrorResponse;
import com.githubrepositorychecker.exception.HeaderNotAcceptableException;
import com.githubrepositorychecker.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("app/")
public class AppController {

    private final ApiClient apiClient;

    public AppController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }


    @GetMapping(value = "repos/{username}")
    public ResponseEntity<?> getUserRepository(@PathVariable String username,
                                               @RequestHeader(value = "Accept") String acceptHeader) throws UserNotFoundException, HttpMediaTypeNotAcceptableException, HeaderNotAcceptableException, HeaderNotAcceptableException {

        try {
            if (acceptHeader.equals("application/xml")) {
                System.out.println("I'm here");
                throw new HeaderNotAcceptableException();
            }

            List<GitRepository> repositories = apiClient.fetchRepository(username);
            return ResponseEntity.ok(repositories);

        } catch (HeaderNotAcceptableException ex) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
        }
    }
}
