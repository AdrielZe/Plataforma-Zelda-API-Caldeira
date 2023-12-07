package com.zelda.zeldaprojeto.userLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoUserLoginService implements UserDetailsService {

    @Autowired
    private UserLoginRepository repository;

    /*Metodo que o Spring vai chamar automaticamente toda vez que fizer o login */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
}
