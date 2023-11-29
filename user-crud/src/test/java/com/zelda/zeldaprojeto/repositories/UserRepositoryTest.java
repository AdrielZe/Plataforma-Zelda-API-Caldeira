package com.zelda.zeldaprojeto.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.zelda.zeldaprojeto.models.UserModel;
import com.zelda.zeldaprojeto.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByNome() {
        // Cria usuário no banco de dados depois salva ele
        UserModel user = new UserModel();
        user.setNome("TestUser");
        userRepository.save(user);

        // Busca usuários pelo nome
        List<UserModel> foundUsers = userRepository.findBynome("TestUser");

        // Checa se o usuário foi encontrado
        assertEquals(1, foundUsers.size());
        UserModel foundUser = foundUsers.get(0);
        assertEquals("TestUser", foundUser.getNome());
    }


}