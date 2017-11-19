package models;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContributorTest {
    private static Contributor target;

    @BeforeClass
    public static void initContributor(){
        target = new Contributor("test", 7);
    }

    @Test
    public void getLogin() throws Exception {
        assertEquals("test", target.getLogin());
    }

    @Test
    public void getCommitCount() throws Exception {
        assertEquals(7, target.getCommitCount());
    }
    @Test
    public void testToString() throws Exception {
        String result = "Login: test; Commits: 7";
        assertEquals(result, target.toString());
    }

}