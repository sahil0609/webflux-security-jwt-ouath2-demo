package org.github.webflux.auth.multipleauth.security;

import org.github.webflux.auth.multipleauth.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component(value = "jwtconvertor")
@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationConvertor  implements ServerAuthenticationConverter {
    private static final String TOKEN_PREFIX = "Bearer ";

    
    private final TokenService JWTtokenService;
    
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
       return Mono.justOrEmpty(exchange)
               .flatMap((exc) -> Mono.justOrEmpty(exc.getRequest().getHeaders().getFirst("Authorization")))
               .map((token) -> token.substring(TOKEN_PREFIX.length()))
               .flatMap(JWTtokenService::validate)
               .flatMap((userDetail) ->Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(userDetail, null, null)));
               
       
    }

    
    
}
