package org.github.webflux.auth.multipleauth.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    
    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        
        return http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .cors()
                .and()
                .build();     
    }
        
    
    
    @Bean
    public AuthenticationWebFilter getJWTFilter(@Qualifier("authManager") ReactiveAuthenticationManager manager) {
        
        AuthenticationWebFilter JWTFilter = new AuthenticationWebFilter(manager);
        JWTFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));
        //TODO add the converter
        
        return JWTFilter;
        
        
        
    }
    
    
}
