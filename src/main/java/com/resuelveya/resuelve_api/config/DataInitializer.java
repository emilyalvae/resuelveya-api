package com.resuelveya.resuelve_api.config;

import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;
import com.resuelveya.resuelve_api.business.data.entity.Usuario;
import com.resuelveya.resuelve_api.business.data.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarUsuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setEmail("admin@resuelveya.com");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setRol(Rol.ADMIN);
                usuarioRepository.save(admin);
            }
        };
    }
}
