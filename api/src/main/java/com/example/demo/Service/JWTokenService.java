package com.example.demo.Service;

import com.example.demo.Entity.JWToken;
import com.example.demo.Repository.JWTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTokenService {
    private final JWTokenRepository jwTokenRepository;

    public JWTokenService(JWTokenRepository jwTokenRepository) {
        this.jwTokenRepository = jwTokenRepository;
    }

    public boolean addToken(JWToken token) {
        jwTokenRepository.save(token);
        return true;
    }

    public boolean isValid(String token) {
         if(jwTokenRepository.findByToken(token)!=null){
             Date date = new Date();
             if(jwTokenRepository.findByToken(token).getData_expiracao().after(date)){
                 return true;
             }
             return false;
         }
         return false;
    }

    public boolean removeToken(String token) {
        jwTokenRepository.deleteByToken(token);
        return true;
    }

}
