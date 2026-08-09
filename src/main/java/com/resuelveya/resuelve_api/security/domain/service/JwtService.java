package com.resuelveya.resuelve_api.security.domain.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class JwtService {
    private final String secret;
    private final long expiration;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    public JwtService(
            @Value("${security.jwt.secret}")
            String secret,

            @Value("${security.jwt.expiration}")
            long expiration
    ){
        this.secret=secret;
        this.expiration=expiration;
    }

    public String generarToken(
            UserDetails userDetails
    ){
        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        logger.info("=== GENERANDO TOKEN JWT ===");
        logger.info("Usuario: {}", userDetails.getUsername());
        logger.info("Autoridades del UserDetails: {}", userDetails.getAuthorities());
        logger.info("Roles extraídos (strings): {}", roles);

        Map<String,Object> claims = Map.of(
                "roles",roles
        );

        Date fechaCreacion = new Date();

        Date fechaExpiracion = new Date(
                fechaCreacion.getTime()+expiration
        );

        String token = Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(fechaCreacion)
                .expiration(fechaExpiracion)
                .signWith(
                        obtenerClave(),
                        Jwts.SIG.HS256
                        )
                .compact();
        
        logger.info("Token generado exitosamente para: {}", userDetails.getUsername());
        logger.info("Token contiene roles: {}", roles);
        
        return token;
    }

    public String obtenerEmail(String token){
        String email = obtenerClaims(token).getSubject();
        logger.debug("Email extraído del token: {}", email);
        return email;
    }

    @SuppressWarnings("unchecked")
    public List<String> obtenerRoles(String token){
        Claims claims = obtenerClaims(token);
        Object rolesObj = claims.get("roles");
        
        logger.info("=== EXTRAYENDO ROLES DEL TOKEN ===");
        logger.info("Objeto roles en el token: {}", rolesObj);
        logger.info("Tipo del objeto: {}", rolesObj != null ? rolesObj.getClass().getName() : "null");
        
        if (rolesObj instanceof List) {
            List<String> rolesList = (List<String>) rolesObj;
            logger.info("Roles encontrados en el token: {}", rolesList);
            return rolesList;
        }
        
        logger.warn("No se encontraron roles válidos en el token. Retornando lista vacía");
        return List.of();
    }

    public boolean esTokenValido(
            String token,
            String username
    ){
        String usernameDelToken = obtenerEmail(token);
        String usernameNormalizado = usernameDelToken != null ? usernameDelToken.trim().toLowerCase() : null;
        String usernamePassedNormalizado = username.trim().toLowerCase();

        return usernameNormalizado != null 
                && usernameNormalizado.equals(usernamePassedNormalizado)
                && !estaExpirado(token);
    }

    public long obtenerTiempoExpiracion(){
        return expiration;
    }

    private SecretKey obtenerClave(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims obtenerClaims(String token){
        return Jwts.parser()
                .verifyWith(obtenerClave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean estaExpirado(String token){
        Date fechaExpiracion = obtenerClaims(token).getExpiration();
        return fechaExpiracion.before(new Date());
    }
}
