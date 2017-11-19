package models;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RepositoryTest {
    private static Repository target;
    private static ArrayList<Contributor> test;

    @BeforeClass
    public static void initRepository(){
        target = new Repository();
        target.setName("test");
        target.setUrl("test url");
        target.setStars(14);
        target.setDescription("test descr");
        target.setLanguage("java");
        target.setCommitsNum(23);

        test = new ArrayList<Contributor>();
        test.add(new Contributor("first", 1));

        target.setTopContributors(test);
    }

    @Test
    public void getName() throws Exception {
        assertEquals("test", target.getName());
    }

    @Test
    public void setName() throws Exception {
        target.setName("repo");
        assertEquals("repo", target.getName());
    }

    @Test
    public void getUrl() throws Exception {
        assertEquals("test url", target.getUrl());
    }

    @Test
    public void setUrl() throws Exception {
        target.setUrl("new url");
        assertEquals("new url", target.getUrl());
    }

    @Test
    public void getStars() throws Exception {
        target.setStars(14);
        assertEquals(14, target.getStars());
    }

    @Test
    public void setStars() throws Exception {
        target.setStars(88);
        assertEquals(88, target.getStars());
    }

    @Test
    public void getDescription() throws Exception {
        target.setDescription("test descr");
        assertEquals("test descr", target.getDescription());
    }

    @Test
    public void setDescription() throws Exception {
        target.setDescription("new descr");
        assertEquals("new descr", target.getDescription());
    }

    @Test
    public void getLanguage() throws Exception {
        target.setLanguage("java");
        assertEquals("java", target.getLanguage());
    }

    @Test
    public void setLanguage() throws Exception {
        target.setLanguage("C#");
        assertEquals("C#", target.getLanguage());
    }

    @Test
    public void getCommitsNum() throws Exception {
        assertEquals(23, target.getCommitsNum());
    }

    @Test
    public void setCommitsNum() throws Exception {
        target.setCommitsNum(41);
        assertEquals(41, target.getCommitsNum());
    }

    @Test
    public void getTopContributors() throws Exception {
        assertEquals(test, target.getTopContributors());
    }

    @Test
    public void setTopContributors() throws Exception {
        ArrayList<Contributor> contrs = new ArrayList<>();
        target.setTopContributors(contrs);
        assertEquals(contrs, target.getTopContributors());
    }

    @Test
    public void testToString() throws Exception {
        String expected = "Name: test URL: test url Stars: 14";
        assertEquals(expected, target.toString());
    }

}