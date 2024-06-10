package com.example.demo.exception;

public class AnswerNotFoundException extends RuntimeException {

    public AnswerNotFoundException(Long id) {
        super("Не найден отве  по id=" + id);
    }
}
