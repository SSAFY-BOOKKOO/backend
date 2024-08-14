package com.ssafy.bookkoo.memberservice.exception;

public class ImageUploadException extends RuntimeException {

    public ImageUploadException() {
        super("프로필 이미지 업로드에 실패하였습니다.");
    }
}
