package com.algaworks.algamoney.api.repository.pessoa;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.model.Pessoa_;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{

    @PersistenceContext
    EntityManager manager;

    @Override
    public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);

        Root<Pessoa> root = criteria.from(Pessoa.class);

        Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Pessoa> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));
    }

    private Long total(PessoaFilter pessoaFilter) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Pessoa> root = criteriaQuery.from(Pessoa.class);

        Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
        criteriaQuery.where(predicates);
        criteriaQuery.select(builder.count(root));

        return manager.createQuery(criteriaQuery).getSingleResult();
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<Pessoa> query, Pageable pageable) {

        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPag = pageable.getPageSize();
        int primeiroRegistroPagina = paginaAtual * totalRegistrosPorPag;

        query.setFirstResult(primeiroRegistroPagina);
        query.setMaxResults(totalRegistrosPorPag);
    }

    private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {

        List<Predicate> predicates = new ArrayList<>();

        if(!ObjectUtils.isEmpty(pessoaFilter.getAtivo())){
            predicates.add(builder.equal(
                    root.get(Pessoa_.ativo),
                    pessoaFilter.getAtivo()
            ));
        }

        if(!ObjectUtils.isEmpty(pessoaFilter.getId())){
            predicates.add(builder.equal(
                    root.get(Pessoa_.id),
                    pessoaFilter.getId()
            ));
        }

        if(!ObjectUtils.isEmpty(pessoaFilter.getNome())){
            predicates.add(builder.like(
                    builder.lower(root.get(Pessoa_.nome)),
                    "%" + pessoaFilter.getNome().toLowerCase() + "%"
            ));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
