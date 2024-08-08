package com.ssafy.bookkoo.memberservice.exception;

public class ProfileImageUploadException extends RuntimeException {

    public ProfileImageUploadException() {
        super("프로필 이미지 업로드에 실패하였습니다.");
    }
}
