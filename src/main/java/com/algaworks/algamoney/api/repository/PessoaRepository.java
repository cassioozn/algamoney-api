package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.pessoa.PessoaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {
}
