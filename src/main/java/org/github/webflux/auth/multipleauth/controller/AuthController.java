package org.github.webflux.auth.multipleauth.controller;

import org.github.webflux.auth.multipleauth.entities.User;
import org.github.webflux.auth.multipleauth.modals.UserDTO;
import org.github.webflux.auth.multipleauth.repository.UserRepository;
import org.github.webflux.auth.multipleauth.security.Provider;
import org.github.webflux.auth.multipleauth.service.TokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ReactiveAuthenticationManager manager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    
    public AuthController(@Qualifier("daoAuthManager") ReactiveAuthenticationManager manager,
            TokenService tokenService, UserRepository userRepository, PasswordEncoder encoder) {

        this.manager = manager;
        this.tokenService= tokenService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    
    
    @PostMapping("/login")
    public Mono<String> login(@RequestBody UserDTO user){
        
        return manager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()))
                .flatMap(tokenService::generateToken);
        
    }
    
    
    @PostMapping("/signup")
    public Mono<ResponseEntity<?>> createUser(@RequestBody UserDTO userDTO){
        
        return Mono.justOrEmpty(userDTO)
        .map((user) -> 
        {
            User mongoUser = new User();
            mongoUser.setEmail(user.getEmail());
            mongoUser.setProvider(Provider.LOCAL);
            mongoUser.setPassword(encoder.encode(user.getPassword()));
            return mongoUser;
        })
        .flatMap((user) -> userRepository.save(user))
        .map((i) -> ResponseEntity.created(null).build());
        
        
        
    }
    
    
}
