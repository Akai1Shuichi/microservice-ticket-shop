package com.example.ticket_service.service;

import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.List;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);
    List<String> extractRoles(String token);
    Long extractUserId(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    Claims extractAllClaims(String token);
    Key getSignInKey();
}
