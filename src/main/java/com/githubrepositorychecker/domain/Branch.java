package com.githubrepositorychecker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Branch {

    private String branchName;
    private int sha;

}
