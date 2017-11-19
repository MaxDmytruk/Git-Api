package services;

import models.Repository;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class OrgJsonServiceTest {
    static OrgJsonService service;
    static String jsonRepository;
    static String jsonArray;
    static String jsonContributor;

    @BeforeClass
    public static void initJson(){
        service = new OrgJsonService();
        jsonRepository = "{\"items\": [{\"full_name\": \"test name\",\"description\": \"test descr\",\"language\": \"C#\",\"stargazers_count\": 432,\"html_url\": \"test.com\"}]}";
        jsonArray = "[\n" +
                "    {\"full_name\": \"test name\",\n" +
                "    \"description\": \"test descr\",\n" +
                "    \"language\": \"C#\",\n" +
                "    \"stargazers_count\": \"432\",\n" +
                "    \"html_url\": \"test.com\"}\n" +
                "  ]";
        jsonContributor = "[{\"sha\":\"938bba32a4008bdde9c064dda6a0597987ddef54\",\"commit\":{\"author\":{\"name\":\"clowwindy\",\"email\":\"clowwindy42@gmail.com\",\"date\":\"2015-08-22T02:45:55Z\"},\"committer\":{\"name\":\"clowwindy\",\"email\":\"clowwindy42@gmail.com\",\"date\":\"2015-08-22T02:45:55Z\"},\"message\":\"remove\",\"tree\":{\"sha\":\"1d4731590099814afd893fa86767b00f7c9809c9\",\"url\":\"https://api.github.com/repos/shadowsocks/shadowsocks/git/trees/1d4731590099814afd893fa86767b00f7c9809c9\"},\"url\":\"https://api.github.com/repos/shadowsocks/shadowsocks/git/commits/938bba32a4008bdde9c064dda6a0597987ddef54\",\"comment_count\":276,\"verification\":{\"verified\":false,\"reason\":\"unsigned\",\"signature\":null,\"payload\":null}},\"url\":\"https://api.github.com/repos/shadowsocks/shadowsocks/commits/938bba32a4008bdde9c064dda6a0597987ddef54\",\"html_url\":\"https://github.com/shadowsocks/shadowsocks/commit/938bba32a4008bdde9c064dda6a0597987ddef54\",\"comments_url\":\"https://api.github.com/repos/shadowsocks/shadowsocks/commits/938bba32a4008bdde9c064dda6a0597987ddef54/comments\",\"author\":{\"login\":\"clowwindy\",\"id\":1073082,\"avatar_url\":\"https://avatars0.githubusercontent.com/u/1073082?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/clowwindy\",\"html_url\":\"https://github.com/clowwindy\",\"followers_url\":\"https://api.github.com/users/clowwindy/followers\",\"following_url\":\"https://api.github.com/users/clowwindy/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/clowwindy/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/clowwindy/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/clowwindy/subscriptions\",\"organizations_url\":\"https://api.github.com/users/clowwindy/orgs\",\"repos_url\":\"https://api.github.com/users/clowwindy/repos\",\"events_url\":\"https://api.github.com/users/clowwindy/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/clowwindy/received_events\",\"type\":\"User\",\"site_admin\":false},\"committer\":{\"login\":\"clowwindy\",\"id\":1073082,\"avatar_url\":\"https://avatars0.githubusercontent.com/u/1073082?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/clowwindy\",\"html_url\":\"https://github.com/clowwindy\",\"followers_url\":\"https://api.github.com/users/clowwindy/followers\",\"following_url\":\"https://api.github.com/users/clowwindy/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/clowwindy/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/clowwindy/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/clowwindy/subscriptions\",\"organizations_url\":\"https://api.github.com/users/clowwindy/orgs\",\"repos_url\":\"https://api.github.com/users/clowwindy/repos\",\"events_url\":\"https://api.github.com/users/clowwindy/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/clowwindy/received_events\",\"type\":\"User\",\"site_admin\":false},\"parents\":[]}]";
    }

    @Test
    public void deserializeToRepository() throws Exception {
        ArrayList<Repository> target = service.DeserializeToRepository(jsonRepository);
        assertEquals(1, target.size());
        Repository testRepos = target.get(0);
        assertEquals("test name", testRepos.getName());
    }

    @Test
    public void getCommitsCount() throws Exception {
        assertEquals(1, service.getCommitsCount(jsonArray));
    }

    @Test
    public void getContributors() throws Exception {
        assertTrue(!service.getContributors(jsonContributor, new HashMap<>()).isEmpty());
    }

}