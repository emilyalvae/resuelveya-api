package com.resuelveya.resuelve_api.security.domain.service.impl;

import com.resuelveya.resuelve_api.business.api.dto.usuario.UsuarioRequestDTO;
import com.resuelveya.resuelve_api.business.api.dto.usuario.UsuarioResponseDTO;
import com.resuelveya.resuelve_api.business.api.exception.RecursoDuplicadoException;
import com.resuelveya.resuelve_api.business.data.entity.Cliente;
import com.resuelveya.resuelve_api.business.data.entity.Tecnico;
import com.resuelveya.resuelve_api.business.data.entity.Usuario;
import com.resuelveya.resuelve_api.business.data.entity.enums.Rol;
import com.resuelveya.resuelve_api.business.data.repository.ClienteRepository;
import com.resuelveya.resuelve_api.business.data.repository.TecnicoRepository;
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

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final TecnicoRepository tecnicoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            TecnicoRepository tecnicoRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UsuarioResponseDTO registrar(UsuarioRequestDTO requestDTO) {
        String username = normalizarEmail(requestDTO.email());

        if (usuarioRepository.existsByEmailIgnoreCase(username)) {
            throw new RecursoDuplicadoException("El correo ya está registrado");
        }

        String encodedPassword = passwordEncoder.encode(requestDTO.password());
        String nombre = requestDTO.nombre().trim();
        String telefono = requestDTO.telefono();
        Rol rol = requestDTO.rol() != null ? requestDTO.rol() : Rol.CLIENTE;

        Usuario usuarioGuardado;

        switch (rol) {
            case TECNICO -> {
                Tecnico tecnico = new Tecnico();
                tecnico.setNombre(nombre);
                tecnico.setEmail(username);
                tecnico.setPassword(encodedPassword);
                tecnico.setTelefono(telefono);
                tecnico.setRol(Rol.TECNICO);
                tecnico.setAniosExperiencia(0);
                tecnico.setCalificacionPromedio(0.0);
                tecnico.setPresentacion(null);
                tecnico.setYapeNumero(null);
                tecnico.setPlinNumero(null);
                tecnico.setTitularPago(null);
                tecnico.setEspecialidad(null);
                usuarioGuardado = tecnicoRepository.saveAndFlush(tecnico);
                tecnicoRepository.registrarFilaTecnicoSiNoExiste(usuarioGuardado.getId());
            }
            case CLIENTE -> {
                Cliente cliente = new Cliente();
                cliente.setNombre(nombre);
                cliente.setEmail(username);
                cliente.setPassword(encodedPassword);
                cliente.setTelefono(telefono);
                cliente.setRol(Rol.CLIENTE);
                cliente.setDireccionHogar(null);
                usuarioGuardado = clienteRepository.saveAndFlush(cliente);
                clienteRepository.registrarFilaClienteSiNoExiste(usuarioGuardado.getId());
            }
            default -> {
                Usuario admin = new Usuario();
                admin.setNombre(nombre);
                admin.setEmail(username);
                admin.setPassword(encodedPassword);
                admin.setTelefono(telefono);
                admin.setRol(Rol.ADMIN);
                usuarioGuardado = usuarioRepository.saveAndFlush(admin);
            }
        }

        return convertirAResponse(usuarioGuardado);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        String username = normalizarEmail(requestDto.email());
        UsernamePasswordAuthenticationToken solicitud = new UsernamePasswordAuthenticationToken(username, requestDto.password());

        UserDetails userDetails = (UserDetails) authenticationManager.authenticate(solicitud).getPrincipal();

        String token = jwtService.generarToken(userDetails);
        String authority = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_CLIENTE");

        Rol rol = Rol.valueOf(authority.replace("ROLE_", ""));
        return new LoginResponseDto(
                token, "Bearer", jwtService.obtenerTiempoExpiracion(),
                userDetails.getUsername(), rol
        );
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }

    private UsuarioResponseDTO convertirAResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getFotoUrl(),
                usuario.getDireccion(),
                usuario.getDistrito(),
                usuario.getCiudad(),
                usuario.getRol()
        );
    }
}
