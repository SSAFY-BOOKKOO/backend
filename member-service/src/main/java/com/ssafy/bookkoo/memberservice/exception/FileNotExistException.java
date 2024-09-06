package com.ssafy.bookkoo.memberservice.exception;

/**
 * 파일이 존재하지 않을 때 발생하는 예외
 */
public class FileNotExistException extends RuntimeException {

    public FileNotExistException() {
        super("변환할 파일이 존재하지 않습니다.");
    }
}