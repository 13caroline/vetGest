package com.example.demo.Security;

import com.example.demo.Entity.Utilizador;
import com.example.demo.Repository.UtilizadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UtilizadorRepository utilizadorRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Utilizador user = utilizadorRepository.findByEmail(s);
        String role=user.getClass().getSimpleName();

        UserDetails u = User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(role)
                .build();

        return u;
    }
}
