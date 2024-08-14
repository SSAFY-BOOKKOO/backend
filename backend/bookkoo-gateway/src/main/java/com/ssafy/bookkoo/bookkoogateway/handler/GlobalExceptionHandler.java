package com.ssafy.bookkoo.bookkoogateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.bookkoo.bookkoogateway.client.AuthServiceWebClient;
import com.ssafy.bookkoo.bookkoogateway.dto.ResponseTokenDto;
import com.ssafy.bookkoo.bookkoogateway.exception.AccessTokenExpirationException;
import com.ssafy.bookkoo.bookkoogateway.exception.MemberNotFoundException;
import com.ssafy.bookkoo.bookkoogateway.exception.RefreshTokenExpirationException;
import com.ssafy.bookkoo.bookkoogateway.exception.WebClientRequestException;
import com.ssafy.bookkoo.bookkoogateway.response.CustomErrorResponse;
import com.ssafy.bookkoo.bookkoogateway.response.CustomErrorResponseWithData;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * GateWay 내부에서 발생하는 예외를 처리하는 핸들러. 기본적으로는 상태 코드와 에러 페이지를 반환함므로 Json 형태로 변경하기 위해 사용
 */
@Slf4j
@Component
@Order(-1)// 기본적으로 사용하는 핸들러보다 우선순위를 높게
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {


    private final ObjectMapper objectMapper;
    private final AuthServiceWebClient authServiceWebClient;
    @Value("${jwt.refreshToken}")
    private String REFRESH_TOKEN;
    public static final Duration REFRESH_TOKEN_EXPIRATION = Duration.ofDays(30);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        //header
        response.getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        //게이트웨이 내부의 예외 핸들링은 여기에 정의
        if (ex instanceof AccessTokenExpirationException accessTokenExpirationException) {
            ServerHttpRequest request = exchange.getRequest();
            return handleAccessTokenExpirationException(request, response,
                accessTokenExpirationException);
        } else if (ex instanceof WebClientRequestException) {
            response.setStatusCode(HttpStatus.BAD_REQUEST);
        } else if (ex instanceof RefreshTokenExpirationException refreshTokenExpirationException) {
            return handleRefreshTokenExpirationException(response, refreshTokenExpirationException);
        } else if (ex instanceof MemberNotFoundException memberNotFoundException) {
            return handleMemberNotFoundException(response, memberNotFoundException);
        } else {
            response.setStatusCode(((ResponseStatusException) ex).getStatusCode());
        }

        return handleDefaultExceptionWithMessage(ex, response);
    }

    /**
     * PASS-PORT를 넣어주어야 할 때 멤버 서비스 호출 중 예외 발생
     * @param response
     * @param memberNotFoundException
     * @return
     */
    private Mono<Void> handleMemberNotFoundException(ServerHttpResponse response,
        MemberNotFoundException memberNotFoundException) {
        // 404 상태코드 세팅
        response.setStatusCode(HttpStatus.NOT_FOUND);
        return handleDefaultExceptionWithMessage(memberNotFoundException, response);
    }

    /**
     * 추가적인 핸들링을 정의하지 않은 경우 예외 처리 공통 로직 분리
     * @param ex
     * @param response
     * @return
     */
    private Mono<Void> handleDefaultExceptionWithMessage(Throwable ex, ServerHttpResponse response) {
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

    /**
     * 리프레시 토큰 만료에 대한 예외 처리
     *
     * @param response
     * @param refreshTokenExpirationException
     * @return
     */
    private Mono<Void> handleRefreshTokenExpirationException(ServerHttpResponse response,
        RefreshTokenExpirationException refreshTokenExpirationException) {
        // 403 상태코드 세팅
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return handleDefaultExceptionWithMessage(refreshTokenExpirationException, response);
    }

    /**
     * 액세스 토큰 만료에 대한 예외 처리 코드 status : 401 message : 액세스 토큰이 만료되었습니다. data : 액세스 토큰
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    private Mono<Void> handleAccessTokenExpirationException(ServerHttpRequest request,
        ServerHttpResponse response, AccessTokenExpirationException ex) {
        // 401 상태코드 세팅
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        // 쿠키에서 리프레시 토큰 가져오기
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        HttpCookie cookie = cookies.getFirst(REFRESH_TOKEN);
        if (cookie == null) {
            //쿠키에 리프레시 토큰이 없으면 RefreshTokenExpirationException를 핸들링
            RefreshTokenExpirationException refreshTokenExpirationException = new RefreshTokenExpirationException();
            return handleDefaultExceptionWithMessage(refreshTokenExpirationException, response);
        }

        // authServiceWebClient로 부터 토큰 가져오기
        return authServiceWebClient.getToken(cookie.getValue())
                                   .flatMap(tokenDto -> {
                                       if (tokenDto == null) {
                                           return Mono.error(new WebClientRequestException(
                                               HttpStatus.INSUFFICIENT_STORAGE));
                                       }
                                       ResponseCookie responseCookie
                                           = ResponseCookie.from(REFRESH_TOKEN,
                                                               tokenDto.refreshToken())
                                                           .path("/")
                                                           .httpOnly(true)
                                                           .maxAge(REFRESH_TOKEN_EXPIRATION)
                                                           .build();
                                        response.addCookie(responseCookie);

                                       CustomErrorResponseWithData<ResponseTokenDto> customErrorResponse = new CustomErrorResponseWithData<>(
                                           response.getStatusCode(), ex.getMessage(), tokenDto);

                                       DataBufferFactory bufferFactory = response.bufferFactory();
                                       try {
                                           byte[] errorResponse = objectMapper.writeValueAsBytes(customErrorResponse);
                                           return response.writeWith(Mono.just(bufferFactory.wrap(errorResponse)));
                                       } catch (Exception e) {
                                           return response.writeWith(Mono.just(bufferFactory.wrap(new byte[0])));
                                       }
                                   })
                                   .onErrorResume(err -> {
                                       DataBufferFactory bufferFactory = response.bufferFactory();
                                       CustomErrorResponse customErrorResponse = new CustomErrorResponse(response.getStatusCode(), ex.getMessage());
                                       try {
                                           byte[] errorResponse = objectMapper.writeValueAsBytes(customErrorResponse);
                                           return response.writeWith(Mono.just(bufferFactory.wrap(errorResponse)));
                                       } catch (Exception e) {
                                           return response.writeWith(Mono.just(bufferFactory.wrap(new byte[0])));
                                       }
                                   });
    }
}
