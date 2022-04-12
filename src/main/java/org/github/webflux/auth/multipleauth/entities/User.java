package org.github.webflux.auth.multipleauth.entities;

import org.github.webflux.auth.multipleauth.security.Provider;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("User")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    private String id;
    
    private String email;
    private String password;
    private Provider provider;
}
