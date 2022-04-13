package org.github.webflux.auth.multipleauth.security;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

//this authenticationmanager is used to authenticate the JWT token in the authentication filter
@Component
@Primary //make this as primary so that it can be picked by webflux security by defualt.
public class AuthManager implements ReactiveAuthenticationManager  {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // doesn't have much to do since the JWT is validated in the Convertor
        //maybe check if the account is blocked or not.
        
        
        return Mono.justOrEmpty(authentication);
    }

}
