package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria atualizar(Long codigo, Categoria categoria){
        Optional<Categoria> categoriaBuscada = categoriaRepository.findById(codigo);

        if(categoriaBuscada.isEmpty()){
            throw new EmptyResultDataAccessException(1);
        }

        Categoria categoriaAuxiliar = categoriaBuscada.get();
        BeanUtils.copyProperties(categoria, categoriaAuxiliar, "codigo");
        return categoriaRepository.save(categoriaAuxiliar);
    }


}
