package com.zelda.zeldaprojeto.controllers;

import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import com.zelda.zeldaprojeto.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    private  UserRepository userRepository;
    private  UserService userService;



    @GetMapping
    public ResponseEntity<Page<UserModel>> acharTodosUsuarios(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable){
        return userService.acharTodosUsuarios(pageable);
    }

    @PostMapping(value = "/adicionar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> adicionarUsuario(@RequestBody UserModel user){
        return userService.adicionarUsuario(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<UserModel>> buscarUsuario(@PathVariable Long id) {
        return userService.buscarUsuario(id);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> editarUsuario(@PathVariable Long id, @RequestBody UserModel user){
       return userService.editarUsuario(id,user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserModel> deletarUsuario(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
