package org.github.webflux.auth.multipleauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedUserException extends ApiException{

    /**
     * 
     */
    private static final long serialVersionUID = 2340053027764579799L;

    public UnAuthorizedUserException(String message) {
        super(message);
    }

  
    
    
    

    
}
