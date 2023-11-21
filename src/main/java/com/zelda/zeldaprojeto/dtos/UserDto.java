package com.zelda.zeldaprojeto.dtos;

import jakarta.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank
    private Long id_user;
    @NotBlank
    private String nome;
    @NotBlank
    private String idade;

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }
}
