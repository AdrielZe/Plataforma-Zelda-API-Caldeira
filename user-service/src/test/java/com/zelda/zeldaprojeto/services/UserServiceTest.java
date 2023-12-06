package com.zelda.zeldaprojeto.services;
import org.junit.jupiter.api.Test;
import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testAcharTodosUsuarios() {
        // Mock do retorno do repositório
        List<UserModel> userList = List.of(
                new UserModel(1L, "Lucas", 25), // Adicione valores apropriados
                new UserModel(2L, "Vizeu", 30)  // Adicione valores apropriados
        );
        Page<UserModel> userPage = new PageImpl<>(userList);

        // Configurar o comportamento do repositório
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        // Act
        ResponseEntity<Page<UserModel>> responseEntity = userService.acharTodosUsuarios(Pageable.unpaged());

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(userPage, responseEntity.getBody());
    }

    @Test
    void testAdicionarUsuario() {
        // Mock do usuário a ser adicionado
        UserModel newUser = new UserModel(1L, "Iago", 22); // Fornecer valores adequados

        // Arrange
        when(userRepository.save(newUser)).thenReturn(newUser);

        //  Act
        ResponseEntity<UserModel> responseEntity = userService.adicionarUsuario(newUser);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(newUser, responseEntity.getBody());
    }

    @Test
    void testBuscarUsuarioQuandoNaoEncontrado() {
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<List<UserModel>> response = userService.buscarUsuario(userId);

        assertEquals(404, response.getStatusCode().value());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testEditarUsuarioQuandoNaoEncontrado() {
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        ResponseEntity<UserModel> response = userService.editarUsuario(userId, new UserModel(1L, "Adriel", 20));

        assertEquals(404, response.getStatusCode().value());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).findById(userId);
        verify(userRepository, never()).save(any(UserModel.class));
    }

    @Test
    void testBuscarUsuarioQuandoNaoEncontradoComDiferenca() {
        // Arrange
        Long userId = 1L;

        //  Act
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //  Act
        ResponseEntity<List<UserModel>> responseEntity = userService.buscarUsuario(userId);

        // Assert
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @Test
    void testEditarUsuarioQuandoEncontrado() {
        // ID do usuário a ser editado
        Long userId = 1L;

        // Arrange
        UserModel updatedUser = new UserModel(1L, "NomeAtualizadoVizeu", 30);

        //  Act
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        //  Act
        ResponseEntity<UserModel> responseEntity = userService.editarUsuario(userId, updatedUser);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(updatedUser, responseEntity.getBody());
    }

    @Test
    void testEditarUsuarioQuandoNaoEncontradoParaEdicao() {
        // ID do usuário a ser editado
        Long userId = 1L;

        // Arrange
        UserModel updatedUser = new UserModel(1L, "NomeAtualizadoVizeu", 30);

        //  Act
        when(userRepository.existsById(userId)).thenReturn(false);

        //  Act
        ResponseEntity<UserModel> responseEntity = userService.editarUsuario(userId, updatedUser);

        // Assert
        assertEquals(404, responseEntity.getStatusCode().value());
    }
}