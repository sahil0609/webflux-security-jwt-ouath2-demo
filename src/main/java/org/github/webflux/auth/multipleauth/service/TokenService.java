package org.github.webflux.auth.multipleauth.service;

import java.util.Base64;
import java.util.Date;

import org.github.webflux.auth.multipleauth.exception.UnAuthorizedUserException;
import org.github.webflux.auth.multipleauth.modals.auth.ValidToken;
import org.github.webflux.auth.multipleauth.security.CustomUserDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import reactor.core.publisher.Mono;

@Service
public class TokenService {


    
    private final String JWTSecret;
    private final Long JWTExpiry;
    
    public TokenService(@Value("${jwt.expiry}") Long expiry, @Value("${jwt.secret}") String secret) {
        
        this.JWTExpiry = expiry;
        this.JWTSecret = secret;
    }
    
    
    public String generateToken(CustomUserDetail principal) {
        
        Date issuedAt = new Date();
        Date Expiry = new Date(issuedAt.getTime() + JWTExpiry);
        
        return Jwts.builder()
        .setIssuer(principal.getUsername())
        .setSubject(principal.getId())
        .setExpiration(Expiry)
        .setIssuedAt(issuedAt)
        .signWith(SignatureAlgorithm.HS512, Base64.getDecoder().decode(JWTSecret.getBytes()))
        .compact();
        
        
    }
    
  public Mono<ValidToken> validate(String token){
      
      return Mono.just(getToken(token))
              .onErrorResume((Error) -> Mono.error(new UnAuthorizedUserException(Error.getMessage())) );
  }
  
  
  public ValidToken getToken(String token) {
      
      Claims claims = Jwts.parser().setSigningKey(Base64.getDecoder().decode(JWTSecret.getBytes()))
      .parseClaimsJws(token)
      .getBody();
      
      if(claims.getExpiration().before(new Date())){
          throw new ExpiredJwtException(null, claims, "token is expired");
      }
      
      return new ValidToken(claims.getSubject(), claims.getIssuer());
      
      
      
  }
    
    
    
}
