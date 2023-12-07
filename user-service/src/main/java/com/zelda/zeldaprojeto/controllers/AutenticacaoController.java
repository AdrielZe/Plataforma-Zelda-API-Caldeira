package com.zelda.zeldaprojeto.controllers;

import com.zelda.zeldaprojeto.infra.security.DadosTokenJWT;
import com.zelda.zeldaprojeto.infra.security.TokenService;
import com.zelda.zeldaprojeto.services.UserService;
import com.zelda.zeldaprojeto.userLogin.DadosAutenticacao;
import com.zelda.zeldaprojeto.userLogin.UserLogin;
import com.zelda.zeldaprojeto.userLogin.UserServiceLogin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserServiceLogin userServiceLogin;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity realizarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); //DTO do próprio Spring, só tem que passar o nosso dto como parametro
            var autentication = manager.authenticate(authenticationToken); //O metodo authenticate(token) recebe o próprio DTO dele, por isso convertemos na linha de cima
            var tokenJWT = tokenService.criarToken((UserLogin) autentication.getPrincipal());
            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarLogin(@RequestBody UserLogin user) {
        return userServiceLogin.cadastrarLogin(user);

    }

}
