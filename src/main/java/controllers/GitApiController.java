package controllers;

import models.Repository;
import services.GitApiService;

import java.util.List;

public class GitApiController {
    GitApiService gitApiServiceervice;

    public GitApiController() {
        this.gitApiServiceervice = new GitApiService();
    }

    public List<Repository> getMostStarredRepositories(){

        return gitApiServiceervice.getMostStarredRpos();
    }

    public List<Repository> getMostCommitedRepositories(){
        return gitApiServiceervice.getMostCommitedRepos(1);
    }

    public int getRepositoryCommit(Repository repository){
        return gitApiServiceervice.getRepositoryCommits(repository);
    }
}
