package com.algaworks.algamoney.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private boolean ativo;

    @Embedded
    private Endereço endereço;

    public Pessoa(){

    }

    public Pessoa(String nome, boolean ativo, Endereço endereço) {
        this.nome = nome;
        this.ativo = ativo;
        this.endereço = endereço;
    }

    public Long getId() {
        return id;
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

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Endereço getEndereço() {
        return endereço;
    }

    public void setEndereço(Endereço endereço) {
        this.endereço = endereço;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) o;
        return getAtivo() == pessoa.getAtivo() && getId().equals(pessoa.getId()) && getNome().equals(pessoa.getNome()) && Objects.equals(getEndereço(), pessoa.getEndereço());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getAtivo(), getEndereço());
    }
}
