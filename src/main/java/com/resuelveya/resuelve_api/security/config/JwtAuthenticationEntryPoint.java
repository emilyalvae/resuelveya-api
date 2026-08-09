package com.resuelveya.resuelve_api.security.config;

import com.resuelveya.resuelve_api.business.api.exception.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    public JwtAuthenticationEntryPoint(            ObjectMapper objectMapper    ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {

        logger.warn("❌ ACCESO NO AUTENTICADO");
        logger.warn("   Ruta: {}", request.getRequestURI());
        logger.warn("   Método: {}", request.getMethod());
        logger.warn("   Excepción: {}", exception.getMessage());
        
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ApiErrorResponse errorResponse =
                new ApiErrorResponse(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        "No se encuentra autenticado o el token no es válido",
                        request.getRequestURI(),
                        Map.of()
                );

        response.setStatus(status.value());
        response.setContentType(
                "application/json"
        );
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(
                response.getOutputStream(),
                errorResponse
        );
    }
}
