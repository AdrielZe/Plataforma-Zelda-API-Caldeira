package com.zelda.zeldaprojeto.controllers;

import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import com.zelda.zeldaprojeto.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> acharTodosUsuarios(){
        return userService.acharTodosUsuarios();
    }

    @PostMapping(value = "/adicionar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> adicionarUsuario(@RequestBody UserModel user){
        return userService.adicionarUsuario(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<UserModel>> buscarUsuario(@PathVariable Long id) {
        return
userService.buscarUsuario(id);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> editarUsuario(@PathVariable Long id, @RequestBody UserModel user){
       return userService.editarUsuario(id,user);
    }

    @DeleteMapping("{id}")
    public void deletarUsuario(@PathVariable Long id){
        userRepository.deleteById(id);
    }

}
