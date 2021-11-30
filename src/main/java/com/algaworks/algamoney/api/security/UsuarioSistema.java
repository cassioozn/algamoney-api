package com.algaworks.algamoney.api.security;

import org.springframework.security.core.userdetails.User;
import com.algaworks.algamoney.api.model.Usuario;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UsuarioSistema extends User{

    public static final long serialVersionUID = 1L;

    Usuario usuario;

    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities){
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
