package com.ssafy.bookkoo.libraryservice.exception;

public class LibraryMoveToSameLibraryException extends RuntimeException {

    public LibraryMoveToSameLibraryException() {
        super("같은 서재로 옮길 수 없습니다.");
    }
}
