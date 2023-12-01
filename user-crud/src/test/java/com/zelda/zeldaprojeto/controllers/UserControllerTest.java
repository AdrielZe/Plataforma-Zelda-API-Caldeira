package com.zelda.zeldaprojeto.controllers;
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

class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    @Test
    void listarUsuarios() throws Exception {
        // Mock do retorno do repositório
        List<UserModel> userList = Arrays.asList(new UserModel(), new UserModel());
        when(userRepository.findAll()).thenReturn(userList);


        // Executar o teste
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]"));

        // Verificar se o método do repositório foi chamado
        verify(userRepository, times(1)).findAll();
    }

    @Test //'UserModel()' cannot be applied to '(long, java.lang.String)'
    void adicionarUsuario() throws Exception {
        String requestBody = "{\"id\": 1, \"nome\": \"NovoUsuario\"}";

        // Mock do retorno do repositório
        UserModel user = new UserModel();
        user.setId(1L);
        user.setNome("NovoUsuario");

        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        // Executar o teste
        MvcResult result = mockMvc.perform(post("/usuarios/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // Adicionar logs
        System.out.println("JSON Retornado: " + result.getResponse().getContentAsString());

        try {
            System.out.println("Resultado do jsonPath para id: " + JsonPath.read(result.getResponse().getContentAsString(), "$.id"));
        } catch (PathNotFoundException e) {
            System.out.println("Erro ao extrair o valor do campo 'id': " + e.getMessage());
        }

        try {
            System.out.println("Resultado do jsonPath para nome: " + JsonPath.read(result.getResponse().getContentAsString(), "$.nome"));
        } catch (PathNotFoundException e) {
            System.out.println("Erro ao extrair o valor do campo 'nome': " + e.getMessage());
        }

        // Verificar se o método do repositório foi chamado
        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    void buscarUsuario() throws Exception {
        // Mock do retorno do repositório
        List<UserModel> userList = Arrays.asList(new UserModel(), new UserModel());
        when(userRepository.findBynome("NomeUsuario")).thenReturn(userList);

        // Executar o teste
        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/NomeUsuario"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{}, {}]"));

        // Verificar se o método do repositório foi chamado
        verify(userRepository, times(1)).findBynome("NomeUsuario");
    }

    @Test
    void editarUsuario() throws Exception {
        String requestBody = "{\"id\": 1, \"nome\": \"NovoNome\"}";

        // Mock do retorno do repositório
        UserModel user = new UserModel();
        user.setId(1L);
        user.setNome("NovoNome");

        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        // Executar o teste
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // Adiciona log
        System.out.println("JSON Retornado: " + result.getResponse().getContentAsString());

        try {
            System.out.println("Resultado do jsonPath para id: " + JsonPath.read(result.getResponse().getContentAsString(), "$.id"));
        } catch (PathNotFoundException e) {
            System.out.println("Erro ao extrair o valor do campo 'id': " + e.getMessage());
        }

        try {
            System.out.println("Resultado do jsonPath para nome: " + JsonPath.read(result.getResponse().getContentAsString(), "$.nome"));
        } catch (PathNotFoundException e) {
            System.out.println("Erro ao extrair o valor do campo 'nome': " + e.getMessage());
        }

        // Verificar se os métodos do repositório foram chamados
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).save(any(UserModel.class));
    }




    @Test
    void deletarUsuario()throws Exception {
        // Mock do retorno do repositório
        doNothing().when(userRepository).deleteById(1L);

        // Executar o teste
        mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verificar se o método foi chamado
        verify(userRepository, times(1)).deleteById(1L);
    }
}