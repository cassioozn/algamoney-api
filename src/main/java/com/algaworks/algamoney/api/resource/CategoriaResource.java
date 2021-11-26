package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;
import com.algaworks.algamoney.api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private CategoriaService categoriaService;

    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    @GetMapping
    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    @PostMapping
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
        Categoria categoriaSalva = categoriaRepository.save(categoria);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo, HttpServletResponse response){

         Optional<Categoria> categoriaBuscada = categoriaRepository.findById(codigo);

         if(categoriaBuscada.isPresent()){
             return ResponseEntity.ok().body(categoriaBuscada.get());
         }
         else {
             return ResponseEntity.notFound().build();
         }
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerCategoria(@PathVariable Long codigo){
        categoriaRepository.deleteById(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> atualizarCategoria(@PathVariable Long codigo, @Valid @RequestBody Categoria categoria){
        Categoria categoriaAuxiliar = categoriaService.atualizar(codigo, categoria);
        return ResponseEntity.ok(categoriaAuxiliar);
    }

}
