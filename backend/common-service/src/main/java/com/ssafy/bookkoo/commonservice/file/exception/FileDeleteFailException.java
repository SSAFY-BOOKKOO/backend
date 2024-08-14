package com.ssafy.bookkoo.commonservice.file.exception;

public class FileDeleteFailException extends RuntimeException {

    public FileDeleteFailException() {
        super("파일 삭제에 실패했습니다.");
    }
}
