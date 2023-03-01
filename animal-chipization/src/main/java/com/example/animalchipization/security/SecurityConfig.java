package com.example.animalchipization.security;

import com.example.animalchipization.data.repositories.AccountRepository;
import com.example.animalchipization.models.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(AccountRepository accountRepository) {
        return email -> {
            Optional<Account> optionalAccount = accountRepository.findByEmail(email);
            if (optionalAccount.isPresent()) {
                return optionalAccount.get();
            } else {
                throw new UsernameNotFoundException("User '" + email + "' not found");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .requestMatchers("/registration").anonymous()
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .build();
    }
}