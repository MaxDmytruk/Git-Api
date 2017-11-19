package services;

import models.Repository;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class OrgJsonService {
    public ArrayList<Repository> deserializeToRepository(String json){
        JSONObject jsonObject = new JSONObject(json);
        JSONArray items = jsonObject.getJSONArray("items");
        ArrayList<Repository> repositories = new ArrayList<>();

        for (Object item : items) {
            JSONObject jsonRepo = (JSONObject) item;
            Repository repository = new Repository();
            repository.setName(jsonRepo.get("full_name").toString());
            repository.setDescription(jsonRepo.isNull("description") ? "" : jsonRepo.get("description").toString());
            repository.setLanguage(jsonRepo.isNull("language") ? "" : jsonRepo.get("language").toString());
            repository.setStars(jsonRepo.getInt("stargazers_count"));
            repository.setUrl(jsonRepo.getString("html_url"));
            repositories.add(repository);
        }

        return repositories;
    }

    public int getCommitsCount(String json){
        try {
            validateJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        JSONArray itemsArray = new JSONArray(json);
        return itemsArray.length();
    }

    public HashMap<String, Integer> getContributors(String json, HashMap<String, Integer> map){
        try {
            validateJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        HashMap<String, Integer> result = map.isEmpty() ? new HashMap<>() : map;
        JSONArray commitsArray = new JSONArray(json);

        String key;
        int value;
        for (Object item : commitsArray){
            JSONObject jsonItem = (JSONObject) item;
            key = jsonItem.isNull("committer") ? "" : jsonItem.getJSONObject("committer").getString("login");
            if(result.keySet().contains(key)){
                value = result.get(key);
                result.replace(key, ++value);
            }
            else{
                result.put(key, 1);
            }
        }
        return result;
    }

    private void validateJson(String json) throws Exception {
        if(json.length() == 0) {
            throw new Exception("json is empty");
        }
    }
}
