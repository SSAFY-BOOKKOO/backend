package com.ssafy.bookkoo.bookkoogateway.handler;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class GlobalServiceExceptionHandler implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(exchange.getRequest()
                                   .getURI() + "this is filter");
        System.out.println(exchange.getResponse()
                                   .getStatusCode());
        return chain.filter(exchange)
                    .doFinally((e) -> {
                        System.out.println("filter 후처리");
                        System.out.println(exchange.getResponse()
                                                   .getStatusCode());

                    });
    }
}
