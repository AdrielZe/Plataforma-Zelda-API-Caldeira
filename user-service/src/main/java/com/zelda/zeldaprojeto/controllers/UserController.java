package com.zelda.zeldaprojeto.controllers;

import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import com.zelda.zeldaprojeto.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.catalina.User;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;



//localhost:8080/swagger-ui.html
@RestController
@RequestMapping(value ="/usuarios", produces = {"application/json"})
@Tag(name = "Zelda-Api")

public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> acharTodosUsuarios(){
        return userService.acharTodosUsuarios();
    }
    @Operation(summary = "Adiciona um usuario", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario adicionado com sucesso!!!"),
            @ApiResponse(responseCode = "422", description = "Usuario de requisição inválido"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),

    })

    @PostMapping(value = "/adicionar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> adicionarUsuario(@RequestBody UserModel user){
        return userService.adicionarUsuario(user);
    }
    @Operation(summary = "Busca dados de usuario pelo seu nome", method = "GET")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })

    @GetMapping("{id}")
    public ResponseEntity<List<UserModel>> buscarUsuario(@PathVariable Long id) {
        return
userService.buscarUsuario(id);
    }
    @Operation(summary = "Editar  usuario pelo seu id", method = "PUT")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edição realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao tentar busca dos dados"),
    })

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> editarUsuario(@PathVariable Long id, @RequestBody UserModel user){
       return userService.editarUsuario(id,user);
    }
    @Operation(summary = "Deletar usuario pelo seu id", method = "DELETE")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados"),
    })

    @DeleteMapping("{id}")
    public void deletarUsuario(@PathVariable Long id){
        userRepository.deleteById(id);
    }

}
