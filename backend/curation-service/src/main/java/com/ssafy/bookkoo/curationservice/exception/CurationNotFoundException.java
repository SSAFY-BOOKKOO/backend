package com.ssafy.bookkoo.curationservice.exception;

public class CurationNotFoundException extends RuntimeException {

    public CurationNotFoundException(Long id) {
        super("큐레이션을 찾을 수가 없어요");
    }

}
