package com.zelda.zeldaprojeto.userLogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    /*Metodo que faz a consulta do usuario na tabela userLogin no banco de dados*/
    UserDetails findByLogin(String login);
}
