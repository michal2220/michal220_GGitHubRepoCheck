package com.githubrepositorychecker;

import com.githubrepositorychecker.domain.Branch;
import com.githubrepositorychecker.domain.GitRepository;
import com.githubrepositorychecker.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig
@WebMvcTest(AppController.class)
public class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiClient apiClient;

    @Test
    void getRepository() throws Exception {
        //Given
        String username = "test";
        List<GitRepository> testList = new ArrayList<>();

        GitRepository repository = new GitRepository();
        repository.setRepositoryName("Test_repository");

        GitRepository.Owner owner = new GitRepository.Owner();
        owner.setOwnerLogin("XYZ");
        repository.setOwnerInfo(owner);
        repository.setFork(false);

        List<Branch> branches = new ArrayList<>();

        Branch branch1 = new Branch();
        branch1.setBranchName("branch1");
        Branch.Commit commit1 = new Branch.Commit();
        commit1.setSha("12asd");
        branch1.setCommit(commit1);

        Branch branch2 = new Branch();
        branch2.setBranchName("branch2");
        Branch.Commit commit2 = new Branch.Commit();
        commit2.setSha("po123");
        branch2.setCommit(commit2);

        branches.add(branch1);
        branches.add(branch2);

        repository.setBranches(branches);

        testList.add(repository);

        //When&Then
        when(apiClient.fetchRepository(username)).thenReturn(testList);

        mockMvc.perform(get("http://localhost:8080/app/repos/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept", "application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].owner.login").value("XYZ"));
    }


    @Test
    public void testInvalidAcceptHeader() throws Exception {
        // Given
        String username = "test";

        //When&Then
        mockMvc.perform(get("/app/repos/{username}", username)
                        .header("Accept", "application/xml"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.status").value("406"));
    }

    @Test
    public void testNonExistentUser() throws Exception {
        // Given
        String username = "test";

        //When&Then
        when(apiClient.fetchRepository(any())).thenThrow(new UserNotFoundException());

        mockMvc.perform(get("/app/repos/{username}", username)
                        .header("Accept", "application/json"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("404"))
                .andExpect(jsonPath("$.message").value("No such user exists."));
    }
}
