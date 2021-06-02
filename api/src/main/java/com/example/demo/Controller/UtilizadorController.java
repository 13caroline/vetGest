package com.example.demo.Controller;

import com.example.demo.Entity.Utilizador;
import com.example.demo.Repository.UtilizadorRepository;
import com.example.demo.Security.AuthenticationRequest;
import com.example.demo.Security.AuthenticationResponse;
import com.example.demo.Security.JwtUtil;
import com.example.demo.Security.MyUserDetailsService;
import com.example.demo.Service.UtilizadorService;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilizadorController {
    @Autowired
    UtilizadorRepository utilizadorRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Qualifier("utilizadorService")
    @Autowired
    private UtilizadorService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin
    @PostMapping(path = "/addUtilizador")
    public Utilizador addUtilizador(@RequestBody Utilizador utilizador){
        return service.saveUtilizador(utilizador);
    }

    @CrossOrigin
    @GetMapping("/utilizador/{email}")
    public Utilizador findAnimalByEmail(@PathVariable String email){
        return service.getUtilizadorByEmail(email);
    }

    @CrossOrigin
    @GetMapping("/")
    public String welcome(){

        return "Welcome to VetGest!!";
    }

    @CrossOrigin
    @RequestMapping(path = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            Utilizador user = utilizadorRepository.findByEmail(authenticationRequest.getUsername());
            if(user!=null && passwordEncoder.matches(authenticationRequest.getPassword(),user.getPassword())){
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
                );
                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(authenticationRequest.getUsername());

                final String jwt = jwtTokenUtil.generateToken(userDetails);
                JSONObject jo = new JSONObject();
                jo.put("dtype", user.getClass().getSimpleName());
                jo.put("jwt", jwt);
                return ResponseEntity.accepted().body(jo.toString());
            }
        }
        catch (BadCredentialsException e) {
            throw new Exception("Email ou password estão errados", e);
        }
        return ResponseEntity.badRequest().body("Email ou password estão errados");
    }
}
