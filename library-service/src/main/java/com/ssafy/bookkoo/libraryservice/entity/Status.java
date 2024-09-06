package com.ssafy.bookkoo.libraryservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 책의 상태를 나타내는 열거형입니다.
 */
@Schema(description = "책의 상태를 나타내는 열거형")
public enum Status {

    @Schema(description = "읽음")
    READ,

    @Schema(description = "읽는 중")
    READING,

    @Schema(description = "찜")
    DIB
}
