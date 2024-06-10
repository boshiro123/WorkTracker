package com.example.demo.controller;

import com.example.demo.entity.NewsData;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.entity.NewsData.parseJsonResponse;

@RestController
@RequestMapping("/api/v2/articles")
public class ArticlesController {
    private static final String API_URL = "https://newsdata.io/api/1/news?apikey=pub_345762b8092a20d9f91f42cec008741f97ecd&language=en&category=technology";
//https://newsapi.org/

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsData> getAll(){
        return getArticles();
    }

    public List<NewsData> getArticles () {
        List<NewsData> list = new ArrayList<>();
        try {
            // Создаем URL-объект и открываем HTTP-соединение
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Устанавливаем метод запроса и получаем ответ
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Читаем ответ в виде строки
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Обрабатываем JSON-ответ
                JSONObject jsonResponse = new JSONObject(response.toString());
                list = parseJsonResponse(jsonResponse.toString());

            } else {
                System.out.println("Ошибка HTTP-запроса. Код статуса: " + responseCode);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при выполнении HTTP-запроса: " + e.getMessage());
        }
        return list;
    }

}
