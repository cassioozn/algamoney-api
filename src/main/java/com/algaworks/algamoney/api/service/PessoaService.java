package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(Long id, Pessoa pessoa){
        Pessoa pessoaAuxiliar = buscarPessoaPeloCodigo(id);
        BeanUtils.copyProperties(pessoa, pessoaAuxiliar, "id");
        return pessoaRepository.save(pessoaAuxiliar);
    }

    public Pessoa buscarPessoaPeloCodigo(Long id) {
        Optional<Pessoa> pessoaBuscada = pessoaRepository.findById(id);

        if (pessoaBuscada.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }

        return pessoaBuscada.get();
    }

    public void atualizarPropriedadeAtivo(Long id, Boolean ativo) {
        Pessoa pessoaAuxiliar = buscarPessoaPeloCodigo(id);

        pessoaAuxiliar.setAtivo(ativo);
        pessoaRepository.save(pessoaAuxiliar);
    }
}
