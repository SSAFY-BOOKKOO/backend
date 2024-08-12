package com.ssafy.bookkoo.commonservice.file.exception;

/**
 * 파일이 존재하지 않을 때 발생하는 예외
 */
public class FileNotExistsException extends RuntimeException {

    public FileNotExistsException(String file) {
        super(file + "이 존재하지 않습니다.");
    }
}
