package com.algaworks.algamoney.api.repository.filter;

import com.algaworks.algamoney.api.model.Endereço;

public class PessoaFilter {

    String nome;
    Boolean ativo;
    Long id;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
