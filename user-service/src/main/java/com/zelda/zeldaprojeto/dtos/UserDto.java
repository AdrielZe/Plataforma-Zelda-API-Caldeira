package com.zelda.zeldaprojeto.dtos;

import jakarta.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private String idade;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
