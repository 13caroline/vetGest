package com.example.demo.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    public SecurityConfigurer(MyUserDetailsService myUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/authenticate","/cliente/registar").permitAll()
                .antMatchers("/addDica/{emailVet}").hasAnyRole("Veterinario")
                //Acessos da Clinica
                .antMatchers("/addVet","/clinica/consultas","/clinica/adicionarClinica","/clinica/clientes",
                        "/clinica/utentes/registar","/clinica/clientes/registar","/clinica/medicos","/clinica/medicos/registar").hasAnyRole("Clinica")
                //Acessos do Cliente
                .antMatchers("/","/cliente","/cliente/animal/registar","/cliente/animal/{id_animal}",
                        "/cliente/animal/cancelar/{id_intervencao}","/cliente/animal/{id_animal}","/cliente/animal/{id_animal}/vacinas",
                        "/cliente/consulta","/cliente/consultas","/cliente/cirurgias","/cliente/preferenciassa","/sobre-nos","/dicas").hasAnyRole("Cliente")

                .antMatchers("/").hasAnyRole("Clinica","Veterinario")
                .antMatchers("/").hasAnyRole("Clinica","Cliente")
                .antMatchers("/").hasAnyRole("Veterinario","Cliente").
                anyRequest().authenticated().and().
                exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
