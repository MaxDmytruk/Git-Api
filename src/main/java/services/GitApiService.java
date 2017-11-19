package services;

import models.Contributor;
import models.Repository;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GitApiService {
    String token = "b43f2d2aa7ed2a702c9f93f5676b37d823ecd548";
    OrgJsonService jsonService;

    public GitApiService() {
        jsonService = new OrgJsonService();
    }

    public List<Repository> getMostStarredRpos() {
        try {
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.github.com")
                    .setPath("/search/repositories")
                    .setParameter("q", "stars:>1000")
                    .setParameter("sort", "stars")
                    .setParameter("order", "desc")
                    .setParameter("page", "1")
                    .setParameter("per_page", "10")
                    .build();

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Authorization", "token " + token);

            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                InputStream inStream = httpEntity.getContent();
                Scanner scanner = new Scanner(inStream);
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNext()) {
                    builder.append(scanner.next());
                }
                String json = builder.toString();
                System.out.println(json);
                ArrayList<Repository> repositories = jsonService.DeserializeToRepository(json);
                for (Repository repository : repositories) {
                    System.out.println("---------------------------");
                    System.out.println("Name: " + repository.getName());
                    System.out.println("Description: " + repository.getDescription());
                    System.out.println("Language: " + repository.getLanguage());
                    System.out.println("Url: " + repository.getUrl());
                    System.out.println("Stars: " + repository.getStars());
                    System.out.println("---------------------------");
                }
                return repositories;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Repository> getMostCommitedRepos(int page) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String query = new StringBuilder()
                    .append("pushed:")
//                    .append(dateFormat.format(startDate))
                    .append("2017-11-01")
                    .append("..")
                    .append("2017-11-07")
//                    .append(dateFormat.format(endDate))
                    .toString();
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.github.com")
                    .setPath("/search/repositories")
                    .setParameter("q", query)
                    .setParameter("sort", "commits")
                    .setParameter("page", String.valueOf(page))
                    .setParameter("per_page", "10")
                    .build();

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Authorization", "token " + token);

            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                InputStream inStream = httpEntity.getContent();

                Scanner scanner = new Scanner(inStream);
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNext()) {
                    builder.append(scanner.next());
                }
                String json = builder.toString();
                System.out.println(json);

                ArrayList<Repository> repositories = jsonService.DeserializeToRepository(json);
                return repositories;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getRepositoryCommits(Repository repository) {
        try{
            int totalCommitsCount = 0;
            int commitsCount = 0;
            int page = 1;
            HashMap<String, Integer> contributors = new HashMap<>();
            do{
                URI uri = new URIBuilder()
                        .setScheme("https")
                        .setHost("api.github.com")
                        .setPath("/repos/" + repository.getName()+"/commits")
                        .setParameter("page", String.valueOf(page++))
                        .setParameter("per_page", "100")
                        .build();

                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet request = new HttpGet(uri);
                request.setHeader("Authorization", "token " + token);

                CloseableHttpResponse response = httpClient.execute(request);
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    InputStream inStream = httpEntity.getContent();

                    Scanner scanner = new Scanner(inStream);
                    StringBuilder builder = new StringBuilder();
                    while (scanner.hasNext()) {
                        builder.append(scanner.next());
                    }
                    String json = builder.toString();
                    contributors = jsonService.getContributors(json, contributors);
                    commitsCount = jsonService.getCommitsCount(json);
                    totalCommitsCount += commitsCount;
                    System.out.println("Commits: " + totalCommitsCount);
                    System.out.println("Contributors: " + contributors.size());
                }
            } while (commitsCount > 0);
            ArrayList<Contributor> bestContributors = getBestContributor(contributors);
            repository.setTopContributors(bestContributors);
            for (Contributor contributor : bestContributors) {
                System.out.println(contributor);
            }
            return totalCommitsCount;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private ArrayList<Contributor> getBestContributor(HashMap<String, Integer> contributors) {
        ArrayList<Contributor> sortedContributors = new ArrayList<>();
        ArrayList<Contributor> bestContributors = new ArrayList<>();
        HashMap<String, Integer> cloneMap = contributors;
        cloneMap.keySet().forEach((key) -> {
            sortedContributors.add(new Contributor(key, cloneMap.get(key)));
        });
        sortedContributors.sort(Comparator.comparing((contributor) -> contributor.getCommitCount()));
        if(sortedContributors.size() >=5) {
            for (int i = sortedContributors.size() - 1; i >= sortedContributors.size() - 5; i--) {
                bestContributors.add(sortedContributors.get(i));
            }
        }
        else{
            for (Contributor contributor : sortedContributors) {
                bestContributors.add(contributor);
            }
        }
        return bestContributors;
    }

}