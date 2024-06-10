package com.example.demo.exception;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(Long id) {
        super("Не найден вопрос по id=" + id);
    }
}
