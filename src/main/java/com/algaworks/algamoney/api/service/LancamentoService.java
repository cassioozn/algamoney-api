package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.exception.CategoriaInexistente;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.BeanUtils;
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

        checarPessoaECategoria(pessoaBuscada, categoriaBuscada);

        return lancamentoRepository.save(lancamento);
    }

    public Lancamento atualizar(Long codigo, Lancamento lancamento) {

        Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);

        if(!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())){
            validarPessoa(lancamento);
        }

        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

        return lancamentoRepository.save(lancamentoSalvo);
    }

    private void validarPessoa(Lancamento lancamento) {
        Pessoa pessoa = null;

        if(lancamento.getPessoa().getId() != null){
            pessoa = (pessoaRepository.findById(lancamento.getPessoa().getId())).get();
        }

        if((pessoaRepository.findById(lancamento.getPessoa().getId())).isEmpty() || !pessoa.getAtivo()){
            throw new PessoaInexistenteOuInativaException();
        }
    }

    private Lancamento buscarLancamentoExistente(Long codigo) {
        Optional<Lancamento> lancamentoSalvo = lancamentoRepository.findById(codigo);

        if(lancamentoSalvo.isEmpty()){
            throw new IllegalArgumentException();
        }

        return lancamentoSalvo.get();
    }

    private void checarPessoaECategoria(Optional<Pessoa> pessoaBuscada, Optional<Categoria> categoriaBuscada){

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
    }
}
