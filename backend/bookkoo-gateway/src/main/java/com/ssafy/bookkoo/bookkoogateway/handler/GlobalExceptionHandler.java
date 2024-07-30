package com.ssafy.bookkoo.bookkoogateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bookkoo.bookkoogateway.exception.TokenExpirationException;
import com.ssafy.bookkoo.bookkoogateway.response.CustomErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * GateWay 내부에서 발생하는 예외를 처리하는 핸들러. 기본적으로는 상태 코드와 에러 페이지를 반환함므로 Json 형태로 변경하기 위해 사용
 */
@Component
@Order(-1)// 기본적으로 사용하는 핸들로보다 우선순위를 높게
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {


    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        //header
        response.getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatusCode());
        } else if (ex instanceof TokenExpirationException) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
        }

        return response
            .writeWith(Mono.fromSupplier(() -> {
                DataBufferFactory bufferFactory = response.bufferFactory();
                try {
                    CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                        response.getStatusCode(),
                        ex.getMessage());
                    byte[] errorResponse = objectMapper.writeValueAsBytes(customErrorResponse);
                    return bufferFactory.wrap(errorResponse);
                } catch (Exception e) {
                    return bufferFactory.wrap(new byte[0]);
                }
            }));
    }
}
