package com.example.demo.Controller;

import com.example.demo.Entity.JWToken;
import com.example.demo.Service.JWTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTokenController {
    @Autowired
    private JWTokenService jwTokenService;

    public boolean addToken(JWToken token){
        return this.jwTokenService.addToken(token);
    }

    public boolean matchToken(String token){
        return this.jwTokenService.isValid(token);
    }

    public boolean removeToken(String token){
        return this.jwTokenService.removeToken(token);
    }

}
