package com.ssafy.bookkoo.libraryservice.exception;

public class LibraryStyleNotFoundException extends RuntimeException {

    public LibraryStyleNotFoundException(Long libraryId) {
        super("Library id : " + libraryId + " 에 대한 서재 스타일이 존재하지 않습니다.");
    }
}
