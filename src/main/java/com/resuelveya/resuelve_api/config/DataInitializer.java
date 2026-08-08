package com.resuelveya.resuelve_api.config;

import com.resuelveya.resuelve_api.data.entity.enums.Rol;
import com.resuelveya.resuelve_api.data.entity.Usuario;
import com.resuelveya.resuelve_api.data.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarUsuarios(UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setEmail("admin@resuelveya.com");
                admin.setRol(Rol.ADMIN);
                usuarioRepository.save(admin);
            }
        };
    }
}
