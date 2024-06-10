package com.example.demo.exception;

public class StatusNotFoundException extends RuntimeException {

    public StatusNotFoundException(Long id) {
        super("Не найден status  по id=" + id);
    }
}
