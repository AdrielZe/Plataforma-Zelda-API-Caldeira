package com.zelda.zeldaprojeto.controllers;

import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

//localhost:8080/swagger-ui/index.html#/
@RestController
@RequestMapping(value ="/usuarios", produces = {"application/json"})
@Tag(name = "Zelda-Api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserModel> listarUsuarios(){
        return userRepository.findAll();
    }

    @Operation(summary = "Adiciona um usuario", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario adicionado com sucesso!!!"),
            @ApiResponse(responseCode = "422", description = "Usuario de requisição inválido"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),

    })

    @PostMapping(value = "/adicionar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserModel adicionarUsuario(@RequestBody UserModel user){
        return userRepository.save(user);
    }

    @Operation(summary = "Busca dados de usuario pelo seu nome", method = "GET")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @GetMapping("{nome}")
    public List<UserModel> buscarUsuario(@PathVariable String nome) {
        return userRepository.findBynome(nome);
    }
    @Operation(summary = "Editar  usuario pelo seu id", method = "PUT")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edição realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao tentar busca dos dados"),
    })
    @PutMapping(value = "{id_user}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserModel editarUsuario(@PathVariable Long id_user, @RequestBody UserModel user){
        if(userRepository.existsById(id_user)){
            user.setId(id_user);
            return userRepository.save(user);
        }
        return null;
    }
    @Operation(summary = "Deletar usuario pelo seu id", method = "DELETE")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })
    @DeleteMapping("{id_user}")
    public void deletarUsuario(@PathVariable Long id_user){
        userRepository.deleteById(id_user);
    }

}
