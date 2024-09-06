package com.ssafy.bookkoo.libraryservice.exception;

/**
 * 서재를 찾을 수 없을 때 발생하는 예외 클래스입니다.
 */
public class LibraryNotFoundException extends RuntimeException {

    /**
     * 주어진 서재 ID에 대한 예외 메시지를 포함하는 생성자입니다.
     *
     * @param libraryId 서재 ID
     */
    public LibraryNotFoundException(Long libraryId) {
        super("id : " + libraryId + " 에 대한 서재가 존재하지 않습니다.");
    }
}
