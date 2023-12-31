package com.githubrepositorychecker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GitRepository {

    @JsonProperty("name")
    private String repositoryName;

    @JsonProperty("owner")
    private Owner ownerInfo;

    public static class Owner {
        @JsonProperty("login")
        private String ownerLogin;

        public String getOwnerLogin() {
            return ownerLogin;
        }

        public void setOwnerLogin(String ownerLogin) {
            this.ownerLogin = ownerLogin;
        }
    }

    @JsonIgnore
    private boolean fork;

    private List<Branch> branches;

    public GitRepository() {
    }
}
