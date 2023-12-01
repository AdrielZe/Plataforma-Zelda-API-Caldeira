package com.zelda.zeldaprojeto.repositories;
import com.zelda.zeldaprojeto.models.UserModel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByNome() {
        // Cria usuário no banco de dados depois salva ele
        UserModel user = new UserModel();
        user.setNome("Lucas Iago Adriel");
        userRepository.save(user);

        // Busca usuários pelo nome
        List<UserModel> foundUsers = userRepository.findBynome("Lucas Iago Adriel");

        // Checa se o usuário foi encontrado
        assertEquals(1, foundUsers.size());
        UserModel foundUser = foundUsers.get(0);
        assertEquals("Lucas Iago Adriel", foundUser.getNome());
    }
}
