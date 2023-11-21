package com.zelda.zeldaprojeto.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")


public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user",nullable = false)
    private Long id_user;
    @Column(name="nome", nullable = false)
    private String nome;
    @Column(name="idade",nullable=false)
    private int idade;

    public void setId(Long id){
        this.id_user = id;
    }

}
