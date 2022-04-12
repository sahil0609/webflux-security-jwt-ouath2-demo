package org.github.webflux.auth.multipleauth.modals.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidToken {

    private String id;
    private String username;
    
}
