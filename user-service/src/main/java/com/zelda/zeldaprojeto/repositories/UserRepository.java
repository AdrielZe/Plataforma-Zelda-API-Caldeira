package com.zelda.zeldaprojeto.repositories;

import com.zelda.zeldaprojeto.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    List<UserModel> findByid(Long id);
}
