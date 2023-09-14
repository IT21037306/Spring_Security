package com.example.springbootkeyclock.configs;

import com.example.springbootkeyclock.middleware.JWTAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration

//For role support
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JWTAuthConverter jwtAuthConverter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {



        //Make all request to authenticate
        httpSecurity
                /*
                    CSRF (Cross Site Resource Forgery)
                        -> Attacker can send embedded a malicious site hyperlink and send it to user
                        -> then when ever user open and enter the details those details will goes to that attacker.
                        -> Order of Working
                            1. Client Log in - Server  generate and sends a CSRF token to client (FrontEnd) as a cookie
                            2. Each time user doing (POST , PUT , DELETE) Operations, This token will send to server and server validate that Token.
                            3. So attacker cannot be able to find that key.
                */
                .csrf(csrf -> csrf.disable())
                        //Authorize Client Request.
                        .authorizeHttpRequests(auth ->

                                //For ,now we are accept any request those are authenticated
                                //But we can specify the pattern also
                                auth.requestMatchers(
                                        "/login",
                                        "/v2/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html"
                                        ).permitAll().anyRequest().authenticated()
                                        );

        httpSecurity
                //This is ,latest method
                .oauth2ResourceServer((oauth )-> oauth.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter)) );

        httpSecurity
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();

    }

}
