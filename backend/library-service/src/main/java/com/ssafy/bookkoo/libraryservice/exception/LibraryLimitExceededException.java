package com.ssafy.bookkoo.libraryservice.exception;

public class LibraryLimitExceededException extends RuntimeException {

    public LibraryLimitExceededException() {
        super("서재를 추가할 수 있는 최대 개수를 초과하였습니다!");
    }

}
