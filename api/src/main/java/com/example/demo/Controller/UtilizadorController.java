package com.example.demo.Controller;

import com.example.demo.Entity.Utilizador;
import com.example.demo.Security.AuthenticationRequest;
import com.example.demo.Security.AuthenticationResponse;
import com.example.demo.Security.JwtUtil;
import com.example.demo.Security.MyUserDetailsService;
import com.example.demo.Service.UtilizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.management.remote.JMXAuthenticator;

@RestController
public class UtilizadorController {


    @Qualifier("utilizadorService")
    @Autowired
    private UtilizadorService service;


    @PostMapping(path = "/addUtilizador")
    public Utilizador addUtilizador(@RequestBody Utilizador utilizador){
        return service.saveUtilizador(utilizador);
    }

    @GetMapping("/utilizador/{email}")
    public Utilizador findAnimalByEmail(@PathVariable String email){
        return service.getUtilizadorByEmail(email);
    }

    @GetMapping("/")
    public String welcome(){
        return "Welcome to VetGest!!";
    }



}
