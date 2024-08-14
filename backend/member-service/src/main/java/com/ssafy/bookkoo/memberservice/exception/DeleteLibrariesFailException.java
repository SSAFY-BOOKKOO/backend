package com.ssafy.bookkoo.memberservice.exception;

/**
 * 회원 탈퇴 시 서재 삭제 에러
 */
public class DeleteLibrariesFailException extends RuntimeException {

    public DeleteLibrariesFailException() {
        super("서재 삭제 과정에서 문제가 발생하였습니다.");
    }
}
