package com.zelda.zeldaprojeto.services;


import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.junit.jupiter.api.Test;
import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import com.zelda.zeldaprojeto.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testAcharTodosUsuarios() {
        // Mock do retorno do repositório
        List<UserModel> userList = List.of(new UserModel(), new UserModel());
        Page<UserModel> userPage = new PageImpl<>(userList);

        // Configurar o comportamento do repositório
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        // Executar o serviço
        ResponseEntity<Page<UserModel>> responseEntity = userService.acharTodosUsuarios(Pageable.unpaged());

        // Verificar o resultado
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(userPage, responseEntity.getBody());
    }

    @Test
    void testAdicionarUsuario() {
        // Mock do usuário a ser adicionado
        UserModel newUser = new UserModel();

        // Configurar o comportamento do repositório
        when(userRepository.save(newUser)).thenReturn(newUser);

        // Executar o serviço
        ResponseEntity<UserModel> responseEntity = userService.adicionarUsuario(newUser);

        // Verificar o resultado
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(newUser, responseEntity.getBody());
    }

    @Test
    void testBuscarUsuarioQuandoNaoEncontrado() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<List<UserModel>> response = userService.buscarUsuario(userId);

        assertEquals(404, response.getStatusCodeValue());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testEditarUsuarioQuandoNaoEncontrado() {
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        ResponseEntity<UserModel> response = userService.editarUsuario(userId, new UserModel());

        assertEquals(404, response.getStatusCodeValue());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).findById(userId);
        verify(userRepository, never()).save(any(UserModel.class));
    }

    @Test
    void testBuscarUsuarioQuandoNaoEncontradoComDiferenca() {
        // ID do usuário a ser buscado
        Long userId = 1L;

        // Configurar o comportamento do repositório
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Executar o serviço
        ResponseEntity<List<UserModel>> responseEntity = userService.buscarUsuario(userId);

        // Verificar o resultado
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    void testEditarUsuarioQuandoEncontrado() {
        // ID do usuário a ser editado
        Long userId = 1L;

        // Mock do usuário
        UserModel updatedUser = new UserModel();
        updatedUser.setId(userId);

        // Configurar o comportamento do repositório
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // Executa
        ResponseEntity<UserModel> responseEntity = userService.editarUsuario(userId, updatedUser);

        // Verificar o resultado
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(updatedUser, responseEntity.getBody());
    }

    @Test
    void testEditarUsuarioQuandoNaoEncontradoParaEdicao() {
        // ID do usuário a ser editado
        Long userId = 1L;

        // Mock do usuário a ser editado
        UserModel updatedUser = new UserModel();

        // Configurar o comportamento do repositório
        when(userRepository.existsById(userId)).thenReturn(false);

        // Executar o serviço
        ResponseEntity<UserModel> responseEntity = userService.editarUsuario(userId, updatedUser);

        // Verificar o resultado
        assertEquals(404, responseEntity.getStatusCodeValue());
    }
}
