package com.ssafy.bookkoo.memberservice.exception;

/**
 * OCR 과정에서 에러 생성 시 발생하는 예외
 */
public class OCRProcessingException extends RuntimeException {

    public OCRProcessingException() {
        super("OCR 프로세싱 과정에서 오류가 발생했습니다.");
    }
}
