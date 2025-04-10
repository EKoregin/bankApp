package ru.korevg.clientapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public String getAccessToken(String clientId, String principal) {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                .withClientRegistrationId(clientId)
                .principal(principal)
                .build();

        var client = authorizedClientManager.authorize(request);
        if (client == null) {
            log.error("Client not authorized");
            throw new RuntimeException("Could not authorize client");
        }
        return  "Bearer " + client.getAccessToken().getTokenValue();
    }
}
