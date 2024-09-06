package com.ssafy.bookkoo.libraryservice.exception;

public class LibraryBookNotFoundException extends RuntimeException {

    public LibraryBookNotFoundException(String message) {
        super(message);
    }
}
