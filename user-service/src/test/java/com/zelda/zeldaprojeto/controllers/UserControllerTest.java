package com.zelda.zeldaprojeto.controllers;

import com.zelda.zeldaprojeto.services.UserService;
import org.junit.jupiter.api.Test;
import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;

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
    void listarUsuarios() throws Exception {
        List<UserModel> userList = Arrays.asList(new UserModel(), new UserModel());
        when(userRepository.findAll()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]"));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void adicionarUsuario() throws Exception {
        String requestBody = "{\"id\": 1, \"nome\": \"NovoUsuario\"}";
        UserModel user = new UserModel();
        user.setId(1L);
        user.setNome("NovoUsuario");

        when(userService.adicionarUsuario(any(UserModel.class))).thenReturn(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(userService, times(1)).adicionarUsuario(any(UserModel.class));
    }

    @Test
    void buscarUsuario() throws Exception {
        // Faz a chamada à API
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Verifica se a resposta é 200 OK
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.emptyString())));

        // Seu JSON esperado
        String expectedJson = "{ \"fieldName\": \"value\" }";

        // Obtém a resposta como String
        String responseContent = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Verifica se a resposta é 200 OK
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Imprime a resposta
        System.out.println("Response Content: " + responseContent);
        // Imprime a resposta e o JSON esperado
        System.out.println("Expected JSON: " + expectedJson);

        // Faz a comparação JSON
        JSONAssert.assertEquals(expectedJson, responseContent, false);
    }

    @Test
    void editarUsuario() throws Exception {
        // Arrange
        Long userId = 1L;
        UserModel userToUpdate = new UserModel();
        userToUpdate.setId(userId);
        userToUpdate.setNome("NovoNome");

        // Simular que o serviço de usuário retorna um usuário atualizado
        when(userService.editarUsuario(anyLong(), any(UserModel.class))).thenReturn(userToUpdate);

        // Act and Assert
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToUpdate)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verificar se o JSON retornado contém as informações esperadas
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("NovoNome"));

        // Verificar se o método do serviço foi chamado
        verify(userService, times(1)).editarUsuario(anyLong(), any(UserModel.class));
    }
    @Test
    void deletarUsuario() throws Exception {
        // Configurar o comportamento do repositório
        doNothing().when(userRepository).deleteById(1L);

        // Executar o teste
        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());

        // Verificar se o método do repositório foi chamado
        verify(userRepository, times(1)).deleteById(1L);
    }

}