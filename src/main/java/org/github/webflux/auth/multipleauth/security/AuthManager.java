package org.github.webflux.auth.multipleauth.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager  {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        //TODO
        
        return Mono.empty();
    }

}
