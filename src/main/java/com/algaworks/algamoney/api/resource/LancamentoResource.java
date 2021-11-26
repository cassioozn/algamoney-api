package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.service.LancamentoService;
import com.algaworks.algamoney.api.service.exception.CategoriaInexistente;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private MessageSource messageSource;

    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping
    public Page<Lancamento> pesquisarLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable){

        return lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarLancamentoPeloCodigo(@PathVariable Long codigo){
        Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);

        if(lancamento.isPresent()){
            return ResponseEntity.ok().body(lancamento.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    @PostMapping
    public ResponseEntity<Lancamento> criarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException( PessoaInexistenteOuInativaException ex){
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();

        List<com.algaworks.algamoney.api.exceptionHandler.ExceptionHandler.Erro> erros = Arrays.asList(new com.algaworks.algamoney.api.exceptionHandler.ExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler({CategoriaInexistente.class})
    public ResponseEntity<Object> handleCategoriaInexistente(CategoriaInexistente ex){
        String mensagemUsuario = messageSource.getMessage("categoria.inexistete", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();

        List<com.algaworks.algamoney.api.exceptionHandler.ExceptionHandler.Erro> erros = Arrays.asList(new com.algaworks.algamoney.api.exceptionHandler.ExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));

        return ResponseEntity.badRequest().body(erros);
    }

    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerCategoria(@PathVariable Long codigo){
        lancamentoRepository.deleteById(codigo);
    }
}
