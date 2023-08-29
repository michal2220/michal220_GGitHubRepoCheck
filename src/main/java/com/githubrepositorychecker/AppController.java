package com.githubrepositorychecker;


import com.githubrepositorychecker.domain.GitRepository;
import com.githubrepositorychecker.exception.HeaderNotAcceptableException;
import com.githubrepositorychecker.exception.UserNotFoundException;
import com.githubrepositorychecker.exception.XmlChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("app/")

public class AppController {

    private final ApiClient apiClient;
    private final XmlChecker xmlChecker;
    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    public AppController(ApiClient apiClient, XmlChecker xmlChecker) {
        this.apiClient = apiClient;
        this.xmlChecker = xmlChecker;
    }

    @GetMapping(value = "repos/{username}")
    public ResponseEntity<List<GitRepository>> getUserRepository(@PathVariable String username, @RequestHeader(value = "Accept") String acceptHeader) throws UserNotFoundException, HeaderNotAcceptableException {

            if (acceptHeader.equals("application/xml")) {
                xmlChecker.checkForXml();
            }

            LOGGER.info("Sending request from controller");
            List<GitRepository> repositories = apiClient.fetchRepository(username);
            return ResponseEntity.ok(repositories);
    }
}
