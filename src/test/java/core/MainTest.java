package core;

import controllers.GitApiController;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void main() throws Exception {
    }

    @Test
    public void measure() throws Exception {
        assertEquals(0, Main.measure(new GitApiController()));
    }

    @Test
    public void bytesToMBytes() throws Exception {
        double result = Main.bytesToMBytes(1048576);
        assertEquals(result, 1, 0);
    }

    @Test
    public void nanoSecondsInSeconds() throws Exception {
        double result = Main.nanoSecondsInSeconds((long)Math.pow(10, 9));
        assertEquals(result, 1 ,0);
    }

}