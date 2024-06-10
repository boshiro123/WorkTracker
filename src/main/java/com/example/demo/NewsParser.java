package com.example.demo;

import com.example.demo.entity.NewsData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsParser {
    public static void main(String[] args) {
        String apiUrl = "https://newsdata.io/api/1/news?apikey=pub_345762b8092a20d9f91f42cec008741f97ecd&language=en&category=technology";
        try {
            String jsonResponse = sendGetRequest(apiUrl);
            List<NewsData> newsList = parseJsonResponse(jsonResponse);
            for (NewsData news : newsList) {
                System.out.println(news.toString());
            }
//        try {
//            JSONObject response = new JSONObject(jsonResponse);
//            JSONArray results = response.getJSONArray("results");
//            System.out.println(results.length());
//            // Извлечение данных для каждой статьи
//            for (int i = 0; i < results.length(); i++) {
//                JSONObject article = results.getJSONObject(i);
//                String articleId = article.getString("article_id");
//                String title = article.getString("title");
//                String link = article.getString("link");
//                JSONArray creators = article.getJSONArray("creator");
//                String description = article.getString("description");
//                String content = article.getString("content");
//                String pubDate = article.getString("pubDate");
////                String imageUrl = article.getString("image_url");
//                String sourceId = article.getString("source_id");
//                int sourcePriority = article.getInt("source_priority");
//                JSONArray countries = article.getJSONArray("country");
//                JSONArray categories = article.getJSONArray("category");
//                String language = article.getString("language");
//
//                // Дальнейшая обработка данных статьи
//                System.out.println("Article ID: " + articleId);
//                System.out.println("Title: " + title);
//                System.out.println("Link: " + link);
//                System.out.println("Creators: " + creators.toString());
//                System.out.println("Description: " + description);
//                System.out.println("Content: " + content);
//                System.out.println("Publication Date: " + pubDate);
//                System.out.println("Source ID: " + sourceId);
//                System.out.println("Source Priority: " + sourcePriority);
//                System.out.println("Countries: " + countries.toString());
//                System.out.println("Categories: " + categories.toString());
//                System.out.println("Language: " + language);
//                System.out.println();
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    public static String sendGetRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            throw new IOException("Failed to retrieve API response. Response code: " + responseCode);
        }
    }

    public static List<NewsData> parseJsonResponse(String jsonResponse) throws JSONException {
        List<NewsData> newsList = new ArrayList<>();
        JSONObject response = new JSONObject(jsonResponse);
        JSONArray results = response.getJSONArray("results");
        System.out.println(results.length());
        for (int i = 0; i < results.length(); i++) {
            JSONObject article = results.getJSONObject(i);
            String title = article.optString("title");
            String author = article.optString("creator");
            String publishedAt = article.optString("pubDate");
            String description = article.optString("description");
            String url = article.optString("link");

            NewsData newsData = new NewsData(title, author, publishedAt, description, url);
            newsList.add(newsData);
        }

        return newsList;
    }
}