package com.zelda.zeldaprojeto.controllers;

import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserModel> listarUsuarios(){
        return userRepository.findAll();
    }

    @PostMapping(value = "/adicionar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserModel adicionarUsuario(@RequestBody UserModel user){
        return userRepository.save(user);
    }

    @GetMapping("{nome}")
    public List<UserModel> buscarUsuario(@PathVariable String nome) {
        return userRepository.findBynome(nome);
    }

    @PutMapping(value = "{id_user}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserModel editarUsuario(@PathVariable Long id_user, @RequestBody UserModel user){
        if(userRepository.existsById(id_user)){
            user.setId(id_user);
            return userRepository.save(user);
        }
        return null;
    }

    @DeleteMapping("{id_user}")
    public void deletarUsuario(@PathVariable Long id_user){
        userRepository.deleteById(id_user);
    }

}
