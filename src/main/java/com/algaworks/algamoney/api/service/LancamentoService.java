package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.exception.CategoriaInexistente;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento salvar(Lancamento lancamento) {

        Optional<Pessoa> pessoaBuscada = (pessoaRepository.findById(lancamento.getPessoa().getId()));
        Optional<Categoria> categoriaBuscada = categoriaRepository.findById(lancamento.getCategoria().getCodigo());

        if(pessoaBuscada.isEmpty()){
            throw new PessoaInexistenteOuInativaException();
        }
        else{
            Pessoa pessoa = pessoaBuscada.get();
            if(!pessoa.getAtivo()){
                throw new PessoaInexistenteOuInativaException();
            }
        }

        if(categoriaBuscada.isEmpty()){
            throw new CategoriaInexistente();
        }

        return lancamentoRepository.save(lancamento);
    }
}
