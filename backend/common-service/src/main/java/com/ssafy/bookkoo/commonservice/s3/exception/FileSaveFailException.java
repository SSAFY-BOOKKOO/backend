package com.ssafy.bookkoo.commonservice.s3.exception;

public class FileSaveFailException extends RuntimeException {

    public FileSaveFailException() {
        super("파일 저장에 실패했습니다.");
    }
}
