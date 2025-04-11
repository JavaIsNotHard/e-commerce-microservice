package com.example.gateway_client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

@RestController
public class TokenController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/api/token")
    public String showToken(@RegisteredOAuth2AuthorizedClient("gateway-client") OAuth2AuthorizedClient authorizedClient, Authentication authentication) {
        OAuth2AuthenticationToken oauth2Auth = (OAuth2AuthenticationToken) authentication;
        String idToken =((DefaultOidcUser) oauth2Auth.getPrincipal()).getIdToken().getTokenValue();
        return "Access Token: " + authorizedClient.getAccessToken().getTokenValue()
                + "Refresh Token" + authorizedClient.getRefreshToken().getTokenValue() + " id Token : " + idToken;
    }

    @GetMapping("/api/logout")
    public Mono<Void> logout(ServerWebExchange exchange) {
        logger.info(exchange.getRequest().getCookies().toString());
        return exchange.getSession().flatMap(WebSession::invalidate).then(Mono.fromRunnable(() -> {
            exchange.getResponse().setStatusCode(HttpStatus.FOUND);
            exchange.getResponse().getHeaders().setLocation(URI.create("/books"));
        })).then();
    }
}
