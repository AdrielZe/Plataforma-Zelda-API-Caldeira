package com.zelda.zeldaprojeto.repositories;
import com.zelda.zeldaprojeto.models.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        // Cria usuário no banco de dados
        UserModel user = new UserModel();
        user.setNome("root");
        user = userRepository.save(user);

        // Busca usuário pelo ID
        Optional<UserModel> foundUser = userRepository.findById(user.getId());

        // Checa se o usuário foi encontrado
        assertTrue(foundUser.isPresent());
        assertEquals("root", foundUser.get().getNome());
    }

    @Test
    public void testFindByNome() {
        // Cria usuário no banco de dados
        UserModel user = new UserModel();
        user.setNome("root");
        userRepository.save(user);

        // Busca usuários pelo nome
        List<UserModel> foundUsers = userRepository.findBynome("root");

        // Checa se o usuário foi encontrado
        assertEquals(1, foundUsers.size());
        UserModel foundUser = foundUsers.get(0);
        assertEquals("root", foundUser.getNome());
    }

    @Test
    public void testFindByNome_WhenNoMatch() {
        // Busca usuários pelo nome que não existe
        List<UserModel> foundUsers = userRepository.findBynome("NonExistentUser");

        // Checa se nenhum usuário foi encontrado
        assertTrue(foundUsers.isEmpty());
    }
}
