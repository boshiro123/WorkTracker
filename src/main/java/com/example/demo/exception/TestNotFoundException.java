package com.example.demo.exception;

public class TestNotFoundException extends RuntimeException {

    public TestNotFoundException(Long id) {
        super("Не найден test по id=" + id);
    }
}
