package com.ssafy.bookkoo.libraryservice.exception;

public class BookAlreadyMappedException extends RuntimeException {

    public BookAlreadyMappedException() {
        super("책이 이미 서재에 들어가있습니다!");
    }
}
