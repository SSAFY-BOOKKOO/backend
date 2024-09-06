package com.ssafy.bookkoo.memberservice.exception;

/**
 * OCR 처리는 완료되었지만, 텍스트를 추출하지 못했습니다.
 */
public class TextNotDetectedException extends RuntimeException {

    public TextNotDetectedException() {
        super("이미지에서 텍스트를 추출하지 못했습니다.");
    }
}
