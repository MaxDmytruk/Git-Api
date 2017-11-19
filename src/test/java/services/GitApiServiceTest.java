package services;

import models.Repository;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GitApiServiceTest {
    static GitApiService service;

    @BeforeClass
    public static void initService(){
        service = new GitApiService();
    }

    @Test
    public void getMostStarredRpos() throws Exception {
        List<Repository> result = service.getMostStarredRpos();
        assertEquals(10, result.size());
    }

    @Test
    public void getMostCommitedRepos() throws Exception {
        List<Repository> result = service.getMostCommitedRepos(1);
        assertEquals(10, result.size());
    }

    @Test
    public void getRepositoryCommits() throws Exception {
        List<Repository> resultList = service.getMostCommitedRepos(1);
        int result = service.getRepositoryCommits(resultList.get(0));
        assertEquals(1686, result);
    }

}