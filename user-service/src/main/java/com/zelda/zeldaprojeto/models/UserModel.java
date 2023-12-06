package com.zelda.zeldaprojeto.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data

@Table(name = "users")

@Builder
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name="nome", nullable = false)
    private String nome;
    @Column(name="idade",nullable=false)
    private int idade;


    // Construtor público
    public UserModel(Long id, String nome, int idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }
    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return this.id;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getNome() {
        return nome;
    }

    public void setIdade(int idade){
        this.idade = idade;
    }

    public void setFieldName(String expectedValue) {
    }
}
