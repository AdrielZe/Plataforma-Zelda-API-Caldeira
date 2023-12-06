package com.zelda.zeldaprojeto.controllers;
import com.zelda.zeldaprojeto.services.UserService;
import org.junit.jupiter.api.Test;
import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertNotNull;



public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void listarUsuarios_null() {
        // Arrange
        when(userService.acharTodosUsuarios(any())).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));

        // Act
        var result = userController.acharTodosUsuarios(null);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }



    @Test
    void buscarUsuario_success() throws Exception {
        // Arrange
        var userId = 1L;
        var mockedResponse = UserModel.builder().id(userId).nome("Test User Iago").idade(25).build();

        when(userService.buscarUsuario(eq(userId))).thenReturn(ResponseEntity.ok(Collections.singletonList(mockedResponse)));

        // Act
        var result = userController.buscarUsuario(userId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(userId, result.getBody().get(0).getId());
    }

    @Test
    void editarUsuario_success() throws Exception {
        // Arrange
        var userId = 1L;
        var userToUpdate = UserModel.builder().id(userId).nome("Updated User Adriel").idade(21).build();

        when(userService.editarUsuario(eq(userId), any())).thenReturn(ResponseEntity.ok(userToUpdate));

        // Act
        var result = userController.editarUsuario(userId, userToUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(userId, result.getBody().getId());
        assertEquals("Updated User Adriel", result.getBody().getNome());
    }

    @Test
    void deletarUsuario() throws Exception {
        // Arrange
        var userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        var result = userController.deletarUsuario(userId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(userRepository, times(1)).deleteById(userId);
    }
}
