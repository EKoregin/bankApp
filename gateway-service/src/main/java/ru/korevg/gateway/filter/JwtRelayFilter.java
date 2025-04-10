package ru.korevg.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRelayFilter implements GlobalFilter, Ordered {

    private final ReactiveOAuth2AuthorizedClientService authorizedClientService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> {
                    if (auth instanceof OAuth2AuthenticationToken oauth2Auth) {
                        return authorizedClientService.loadAuthorizedClient(
                                        oauth2Auth.getAuthorizedClientRegistrationId(),
                                        oauth2Auth.getName()
                                )
                                .doOnNext(authClient -> log.info("User name: {}", authClient.getPrincipalName()))
                                .map(OAuth2AuthorizedClient::getAccessToken)
                                .map(accessToken -> {
                                    String tokenValue = accessToken.getTokenValue();
                                    log.info("Relaying JWT access token to downstream service: {}...", tokenValue);
                                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenValue)
                                            .build();
                                    return exchange.mutate().request(mutatedRequest).build();
                                })
                                .defaultIfEmpty(exchange);
                    } else if (auth instanceof JwtAuthenticationToken jwtAuth) {
                        log.debug("Request already has JwtAuthenticationToken, skipping token relay.");
                        return Mono.just(exchange);
                    } else {
                        log.warn("Unhandled authentication type: {}, skipping JwtRelayFilter", auth.getClass());
                        return Mono.just(exchange);
                    }
                })
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
