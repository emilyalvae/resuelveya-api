package com.resuelveya.resuelve_api.business.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> manejarRecursoNoEncontrado(
            RecursoNoEncontradoException exception,
            HttpServletRequest request
    ){
        return construirRespuesta(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI(),
                Map.of()
        );
    }
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ApiErrorResponse> manejarRecursoDuplicado(
            RecursoDuplicadoException exception,
            HttpServletRequest request
    ){
        return construirRespuesta(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler(RolInvalidoException.class)
    public ResponseEntity<ApiErrorResponse> manejarRolInvalido(
            RolInvalidoException exception,
            HttpServletRequest request
    ){
        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "El rol es inválido",
                request.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> manejarExcepcionGeneral(
            Exception exception,
            HttpServletRequest request
    ) {
        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno en el servidor",
                request.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> manejarAutenticacion(
            AuthenticationException exception,
            HttpServletRequest request
    ) {
        return construirRespuesta(
                HttpStatus.UNAUTHORIZED,
                "Usuario o contraseña incorrectos",
                request.getRequestURI(),
                Map.of()
        );
    }

    private  ResponseEntity<ApiErrorResponse> construirRespuesta(
            HttpStatus status,
            String message,
            String path,
            Map<String,String> validateErrors
    ){
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path,validateErrors
        );
        return ResponseEntity.status(status).body(response);
    }
}
