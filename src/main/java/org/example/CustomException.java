package org.example;

public class CustomException extends Exception {
    public CustomException(ExceptionType e) {
        super(e.getMessage());
    }
}
