package com.zelda.zeldaprojeto.userLogin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceLogin {

    @Autowired
    private UserLoginRepository userLoginRepository;

    public ResponseEntity<UserLogin> cadastrarLogin(UserLogin user) {
        return ResponseEntity.ok(userLoginRepository.save(user));
    }

}
