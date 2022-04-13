package org.github.webflux.auth.multipleauth.config;

import org.github.webflux.auth.multipleauth.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    
    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http, @Qualifier("jwtfilter") AuthenticationWebFilter filter) {
        
        return http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .cors()
                .and()
                
                // adding the authenticated methods
                .authorizeExchange()
                .pathMatchers("/auth/*")
                .permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) // make the app stateless
                
                //adding the authorization filter
                .addFilterAt(filter,SecurityWebFiltersOrder.AUTHENTICATION)
                .build();     
    }
        
    
    
    @Bean("jwtfilter")
    public AuthenticationWebFilter getJWTFilter(@Qualifier("authManager") ReactiveAuthenticationManager manager,
            @Qualifier("jwtconvertor") ServerAuthenticationConverter JWTConvertor) {
        
        AuthenticationWebFilter JWTFilter = new AuthenticationWebFilter(manager);
        JWTFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**")); // run for every request
        JWTFilter.setServerAuthenticationConverter(JWTConvertor);
        
        return JWTFilter;
        
        
        
    }
    
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        
        return new BCryptPasswordEncoder();
    }
    
    
    @Bean("daoAuthManager") 
    @Autowired
    // Authentication manager to be used to initially authenticate our username and password
    // will not be used in the chain since we disabled http basic and form login
    public ReactiveAuthenticationManager getDAOAuthenticationManager(UserDetailService userService) {
        
        UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(userService);
        manager.setPasswordEncoder(getPasswordEncoder());
        
        return manager;
        
    }
    
    
}
