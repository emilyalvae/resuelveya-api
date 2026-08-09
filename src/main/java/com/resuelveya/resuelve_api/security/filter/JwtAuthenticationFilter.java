package com.resuelveya.resuelve_api.security.filter;

import com.resuelveya.resuelve_api.security.domain.service.CustomUserDetailsService;
import com.resuelveya.resuelve_api.security.domain.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        logger.info("=== JWT AUTHENTICATION FILTER ===");
        logger.info("Ruta solicitada: {} {}", request.getMethod(), request.getRequestURI());
        logger.info("Header Authorization recibido: {}", authorizationHeader != null ? authorizationHeader.substring(0, Math.min(20, authorizationHeader.length())) + "..." : "NULL");
        
        if (authorizationHeader == null         || !authorizationHeader.startsWith(                BEARER_PREFIX        )) {
            logger.info("⚠️  Sin token o formato incorrecto. Continuando sin autenticación JWT");
            filterChain.doFilter(request, response);
            return;
        }

        logger.info("✓ Token encontrado en header Authorization");
        
        String token = authorizationHeader.substring(                BEARER_PREFIX.length()        );

        logger.info("Token extraído (primeros 50 chars): {}", token.substring(0, Math.min(50, token.length())));
        
        try {
            autenticarUsuario(                    token,                    request            );
        } catch (JwtException exception) {
            logger.warn("❌ Token JWT inválido o expirado: {}", exception.getMessage());
            SecurityContextHolder.clearContext();
        } catch (IllegalArgumentException exception) {
            logger.warn("❌ Error procesando el token JWT: {}", exception.getMessage());
            SecurityContextHolder.clearContext();
        } catch (Exception exception) {
            logger.error("❌ Error inesperado en JWT filter: {}", exception.getMessage(), exception);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(            String token,            HttpServletRequest request    ) {
        String username =                jwtService.obtenerEmail(token);

        boolean noEstaAutenticado =                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null;

        if (username == null || !noEstaAutenticado) {
            if (username == null) {
                logger.debug("No se pudo extraer el email del token JWT");
            }
            return;
        }

        String emailNormalizado = username.trim().toLowerCase();
        logger.info("=== PROCESANDO TOKEN JWT EN FILTRO ===");
        logger.info("Email extraído del token (normalizado): {}", emailNormalizado);
        
        // Extraer roles del token
        List<String> rolesDelToken = jwtService.obtenerRoles(token);
        logger.info("Roles extraídos del token: {}", rolesDelToken);
        logger.info("Número de roles: {}", rolesDelToken.size());
        
        // Convertir roles a GrantedAuthority
        List<? extends GrantedAuthority> authorities = rolesDelToken.stream()
                .map(role -> {
                    logger.info("Convirtiendo rol a SimpleGrantedAuthority: {} -> {}", role, new SimpleGrantedAuthority(role).getAuthority());
                    return new SimpleGrantedAuthority(role);
                })
                .toList();
        
        logger.info("Autoridades construidas: {}", authorities);
        logger.info("Total de autoridades: {}", authorities.size());
        
        if (!jwtService.esTokenValido(token, username)) {
            logger.warn("Token JWT no válido para el usuario: {}", emailNormalizado);
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        emailNormalizado,
                        null,
                        authorities
                );

        authentication.setDetails(                new WebAuthenticationDetailsSource()
                        .buildDetails(request)
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        
        logger.info("✅ Usuario {} autenticado exitosamente en SecurityContext", emailNormalizado);
        logger.info("   Autoridades asignadas: {}", authentication.getAuthorities());
        logger.info("   IsAuthenticated: {}", authentication.isAuthenticated());
    }



}