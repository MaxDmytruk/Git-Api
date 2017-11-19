package core;

import controllers.GitApiController;
import models.Repository;

import java.util.List;

public class Main {
    private static final double BYTES_IN_MEGABYTE = 1048576.0;

    public static void main(String[] args) {
        GitApiController controller = new GitApiController();
        measure(controller);
    }

    public static int measure(GitApiController controller){
        long startTime;
        long finishTime;
        long measureTimeInNanoSeconds;
        long startMemory;
        long finishMemory;
        long resultMemory;
        double measureTimeInSeconds;
        double resultMBytes;

        startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
        controller.getMostStarredRepositories();
        finishTime = System.nanoTime();
        measureTimeInNanoSeconds = finishTime - startTime;
        measureTimeInSeconds = nanoSecondsInSeconds(measureTimeInNanoSeconds);
        System.out.println("Get repositories with most stars ends with: " + measureTimeInSeconds + " s");
        finishMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        resultMemory = finishMemory - startMemory;
        resultMBytes = bytesToMBytes(resultMemory);
        System.out.println("Get repositories with most stars use: " + resultMBytes + " Mb");

        startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
        List<Repository> repos = controller.getMostCommitedRepositories();

        for (Repository repository : repos) {
            controller.getRepositoryCommit(repository);
        }

        finishTime = System.nanoTime();
        measureTimeInNanoSeconds = finishTime - startTime;
        measureTimeInSeconds = nanoSecondsInSeconds(measureTimeInNanoSeconds);
        System.out.println("Get most commited repositories takes " + measureTimeInSeconds + " s");
        finishMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        resultMemory = finishMemory - startMemory;
        resultMBytes = bytesToMBytes(resultMemory);
        System.out.println("Get most commited repositories use: " + resultMBytes + " Mb");
        return 0;
    }

    public static double bytesToMBytes(long bytes){
        return bytes / BYTES_IN_MEGABYTE;
    }

    public static double nanoSecondsInSeconds(long nano){
        return nano*Math.pow(10, -9);
    }
}
