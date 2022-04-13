package org.github.webflux.auth.multipleauth.controller;

import java.security.Principal;

import org.github.webflux.auth.multipleauth.modals.UserDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/me")
public class UserController {

    
    @GetMapping("/")
    public Mono<UserDTO> getUser(@AuthenticationPrincipal Mono<UserDetails> principal){
        
      return principal.map((user) -> {
          var userDTO = new UserDTO();
          userDTO.setEmail(user.getUsername());
          return userDTO;
      });
              
        
        
    }
    
    
}
