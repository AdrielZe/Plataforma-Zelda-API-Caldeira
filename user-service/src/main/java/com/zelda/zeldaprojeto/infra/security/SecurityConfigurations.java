package com.zelda.zeldaprojeto.infra.security;

import jakarta.ws.rs.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Para indicar ao Spring que vou personalizar as configurações de segurança
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean //Serve para expor a classe para o spring e fazendo que ele consiga fazer a injeção de dependência em outras classes
    /*Desabilitando o CSRF e habilitando a autenticação stateless do Spring */
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    /*Esta classe AuthenticationConfiguration tem esse metodo getAuthenticationManager que sabe criar o objeto authenticationManager*/
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    /*Mostrando para o spring que é para usar esse tipo de hash de senha*/
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

}
