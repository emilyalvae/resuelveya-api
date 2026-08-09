package com.resuelveya.resuelve_api.security.domain.service;

import com.resuelveya.resuelve_api.business.data.entity.Usuario;
import com.resuelveya.resuelve_api.business.data.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(
            UsuarioRepository usuarioRepository
    ){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String emailNormalizado =  email.trim().toLowerCase();

        Usuario usuario = usuarioRepository
                .findByEmailIgnoreCase(emailNormalizado)
                .orElseThrow(()->
                        new UsernameNotFoundException("Email o contraseña incorrectos"));

        String autorizacion =  "ROLE_"+usuario.getRol();

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(new SimpleGrantedAuthority(autorizacion))
                .build();
    }
}
