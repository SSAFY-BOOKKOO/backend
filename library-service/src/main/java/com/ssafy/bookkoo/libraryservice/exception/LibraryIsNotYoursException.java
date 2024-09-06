package com.ssafy.bookkoo.libraryservice.exception;

public class LibraryIsNotYoursException extends RuntimeException {

    public LibraryIsNotYoursException() {
        super("이 서재는 당신의 서재가 아닙니다.");
    }
}
