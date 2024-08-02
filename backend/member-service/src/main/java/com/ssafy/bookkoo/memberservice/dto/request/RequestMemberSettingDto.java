package com.ssafy.bookkoo.memberservice.dto.request;

import com.ssafy.bookkoo.memberservice.enums.ReviewVisibility;
import lombok.Builder;

@Builder
public record RequestMemberSettingDto(
    Boolean isLetterReceive,
    ReviewVisibility reviewVisibility
) {

    public RequestMemberSettingDto {
        if (isLetterReceive == null) {
            isLetterReceive = true;
        }
        if (reviewVisibility == null) {
            reviewVisibility = ReviewVisibility.PUBLIC;
        }
    }
}