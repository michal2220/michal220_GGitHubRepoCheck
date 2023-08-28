package com.githubrepositorychecker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Branch {

    @JsonProperty("name")
    private String branchName;

    @JsonProperty("commit")
    private Commit commit;

    public static class Commit {
        @JsonProperty("sha")
        private String sha;
    }

    public Branch() {
    }
}
