package com.ssafy.bookkoo.commonservice.email.dto;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AwsSesDto {

    public static final String FROM_EMAIL = "bookkoobookkoo624@gmail.com"; // 보내는 사람

    private final List<String> to; // 받는 사람
    private final String subject; // 제목
    private final String content; // 본문

    @Builder
    public AwsSesDto(final List<String> to, final String subject,
        final String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public SendEmailRequest toSendRequestDto() {
        final Destination destination = new Destination()
            .withToAddresses(this.to);

        final Message message = new Message()
            .withSubject(createContent(this.subject))
            .withBody(new Body()
                .withHtml(createContent(this.content)));

        return new SendEmailRequest()
            .withSource(FROM_EMAIL)
            .withDestination(destination)
            .withMessage(message);
    }

    private Content createContent(final String text) {
        return new Content()
            .withCharset("UTF-8")
            .withData(text);
    }
}
