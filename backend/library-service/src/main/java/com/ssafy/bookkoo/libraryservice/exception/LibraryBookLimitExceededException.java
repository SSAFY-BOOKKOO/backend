package com.ssafy.bookkoo.libraryservice.exception;

public class LibraryBookLimitExceededException extends RuntimeException {

    public LibraryBookLimitExceededException() {
        super("서재 내에 등록가능한 책의 권수를 초과하였습니다.");
    }

}
