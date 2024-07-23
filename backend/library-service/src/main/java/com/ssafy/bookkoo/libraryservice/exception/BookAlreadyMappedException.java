package com.ssafy.bookkoo.libraryservice.exception;

public class BookAlreadyMappedException extends RuntimeException {

    public BookAlreadyMappedException(String message) {
        super(message);
    }
}
