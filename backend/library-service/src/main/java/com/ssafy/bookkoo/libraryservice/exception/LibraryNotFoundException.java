package com.ssafy.bookkoo.libraryservice.exception;

public class LibraryNotFoundException extends RuntimeException {

    public LibraryNotFoundException(Long libraryId) {
        super("id : " + libraryId + " 에 대한 서재가 존재하지 않습니다.");
    }
}
