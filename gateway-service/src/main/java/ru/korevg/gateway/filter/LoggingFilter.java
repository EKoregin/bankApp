package ru.korevg.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Request URI: {}", exchange.getRequest().getURI());
        log.info("Request Method: {}", exchange.getRequest().getMethod());
        log.info("Request Headers: {}", exchange.getRequest().getHeaders());

        return chain.filter(exchange).doOnSuccess(aVoid -> {
            log.info("Response Status Code: {}", exchange.getResponse().getStatusCode());
        });
    }
}
