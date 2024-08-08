package com.ssafy.bookkoo.commonservice.s3.exception;

public class FileDeleteFailException extends RuntimeException {

    public FileDeleteFailException() {
        super("파일 삭제에 실패했습니다.");
    }
}
