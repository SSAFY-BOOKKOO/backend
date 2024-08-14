package com.ssafy.bookkoo.bookkoogateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
@Slf4j
public class GlobalServiceExceptionHandler implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("request: {} : {}", exchange.getRequest()
                                             .getMethod(), exchange.getRequest()
                                                                   .getURI());
        log.info("status code : {}", exchange.getResponse()
                                             .getStatusCode());
        return chain.filter(exchange)
                    .doFinally((e) -> {
                    });
    }
}
