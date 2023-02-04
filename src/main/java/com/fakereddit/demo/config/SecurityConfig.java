package com.fakereddit.demo.config;

import com.fakereddit.demo.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.httpBasic().disable();
//        http.csrf().disable().authorizeRequests().anyRequest().permitAll();
        http.cors().and().csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/home/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/subreddit").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/posts/").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/posts/**").permitAll()
//                        .requestMatchers("/api/subreddit").permitAll()
                        .requestMatchers("/swagger-ui/**",
                                "/swagger-ui*/**",
                                "/v3/api-docs/**",
                                "/configuration/ui",
                                "/swagger-resources/**",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/webjars/**"
                                ).permitAll()
                        .anyRequest().authenticated()
                );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        int strength = 10;  // work factor of bcrypt
        BCryptPasswordEncoder securedEncoder = new BCryptPasswordEncoder(strength,new SecureRandom());
        return securedEncoder;
    }

}
