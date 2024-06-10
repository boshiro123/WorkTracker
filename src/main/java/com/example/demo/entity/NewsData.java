package com.example.demo.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsData {
    private String title;
    private String author;
    private String publishedAt;
    private String description;
    private String url;
    // Добавьте остальные поля из вашего API

    public NewsData(String title, String author, String publishedAt, String description, String url) {
        this.title = title;
        this.author = author;
        this.publishedAt = publishedAt;
        this.description = description;
        this.url = url;
        // Инициализируйте остальные поля из вашего API
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    // Добавьте методы доступа для остальных полей из вашего API

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

    @Override
    public String toString() {
        return "NewsData{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}