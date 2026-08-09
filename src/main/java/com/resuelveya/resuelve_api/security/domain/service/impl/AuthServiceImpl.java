package com.resuelveya.resuelve_api.security.domain.service.impl;

import com.resuelveya.resuelve_api.business.api.dto.usuario.UsuarioRequestDTO;
import com.resuelveya.resuelve_api.business.api.dto.usuario.UsuarioResponseDTO;
import com.resuelveya.resuelve_api.business.api.exception.RecursoDuplicadoException;
import com.resuelveya.resuelve_api.business.data.entity.Usuario;
import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;
import com.resuelveya.resuelve_api.business.data.repository.UsuarioRepository;
import com.resuelveya.resuelve_api.security.api.dto.LoginRequestDto;
import com.resuelveya.resuelve_api.security.api.dto.LoginResponseDto;
import com.resuelveya.resuelve_api.security.domain.service.AuthService;
import com.resuelveya.resuelve_api.security.domain.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private  final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @Override
    public UsuarioResponseDTO registrar(UsuarioRequestDTO requestDTO) {
        String username= normalizarEmail(requestDTO.email());

        if (usuarioRepository.existsByEmailIgnoreCase(username)){
            throw  new RecursoDuplicadoException("El correo ya está registrado");
        }

        Usuario usuario = new Usuario();

        usuario.setEmail(username);
        usuario.setPassword(
                passwordEncoder.encode(requestDTO.password())
        );

        usuario.setNombre(requestDTO.nombre().trim());

        usuario.setRol(requestDTO.rol());

        Usuario usuarioGuardado=usuarioRepository.save(usuario);
        return convertirAResponse(usuarioGuardado);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
       String username = normalizarEmail(requestDto.email());
        UsernamePasswordAuthenticationToken solicitud = new UsernamePasswordAuthenticationToken(username,requestDto.password());

        UserDetails userDetails = (UserDetails) authenticationManager.authenticate(solicitud).getPrincipal();

        String token = jwtService.generarToken(userDetails);
        String authority = userDetails                .getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_CLIENTE");

        Rol rol = Rol.valueOf(authority.replace("ROLE_", ""));
        return  new LoginResponseDto(
                token,"Bearer",jwtService.obtenerTiempoExpiracion(),
                userDetails.getUsername(),rol
        );

    }

    private String normalizarEmail(
            String email
    ) {
        return email
                .trim()
                .toLowerCase();
    }

    private UsuarioResponseDTO convertirAResponse(Usuario usuario){
        return  new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getRol()
        );
    }
}
