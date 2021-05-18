package com.example.demo;

import com.example.demo.Security.AuthenticationRequest;
import com.example.demo.Security.AuthenticationResponse;
import com.example.demo.Security.JwtUtil;
import com.example.demo.Security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@SpringBootApplication
public class VetgestApplication {


	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;

	public static void main(String[] args) {
		SpringApplication.run(VetgestApplication.class, args);
	}

	@RequestMapping(path = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			System.out.println("\n\nAQUI\n\n");
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect email or password", e);
		}

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
