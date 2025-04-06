package ru.korevg.clientapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@Configuration
public class TokenFetcher {

    @Value("${spring.security.oauth2.client.registration.bankAppClient.client-id}")
    private String clientId;

    @Bean
    @RequestScope
    public OAuth2AccessToken fetchToken(OAuth2AuthorizedClientService authorizedClientService) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AccessToken accessToken = null;
        if (auth.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) auth;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            if (clientRegistrationId.equals(clientId)) {
                OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(clientRegistrationId, auth.getName());
                accessToken = client.getAccessToken();
            }
        }
        return accessToken;
    }
}
