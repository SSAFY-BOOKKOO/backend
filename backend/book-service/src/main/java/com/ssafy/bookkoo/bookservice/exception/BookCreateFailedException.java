package com.ssafy.bookkoo.bookservice.exception;

public class BookCreateFailedException extends RuntimeException {

    public BookCreateFailedException(String message) {
        super(message);
    }
}
