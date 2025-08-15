package com.example.ticket_service.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex) {
        ex.printStackTrace(); // TODO: gửi lên observability tool nếu cần

        HttpStatusCode status = switch (ex) {
            case BadCredentialsException e     -> HttpStatusCode.valueOf(401);
            case AccountStatusException e      -> HttpStatusCode.valueOf(403);
            case AccessDeniedException e       -> HttpStatusCode.valueOf(403);
            case SignatureException e          -> HttpStatusCode.valueOf(403);
            case ExpiredJwtException e         -> HttpStatusCode.valueOf(403);
            default                            -> HttpStatusCode.valueOf(500);
        };

        return ProblemDetail.forStatusAndDetail(status, ex.getMessage());
    }
}

