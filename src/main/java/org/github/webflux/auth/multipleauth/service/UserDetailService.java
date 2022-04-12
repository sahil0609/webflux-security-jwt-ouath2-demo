package org.github.webflux.auth.multipleauth.service;

import org.github.webflux.auth.multipleauth.repository.UserRepository;
import org.github.webflux.auth.multipleauth.security.CustomUserDetail;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserDetailService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    
    
    @Override
    public Mono<UserDetails> findByUsername(String username) {
       // currently return one hard coded name
       // can change the implementation to get the data from a database repository
       
               
       return userRepository.findByEmail(username)
               .map((user) -> new CustomUserDetail(user.getId(), user.getPassword(), user.getEmail(), null));
      
        
    }

}
