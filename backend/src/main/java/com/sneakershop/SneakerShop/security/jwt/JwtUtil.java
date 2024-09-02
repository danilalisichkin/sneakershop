package com.sneakershop.SneakerShop.security.jwt;

import com.sneakershop.SneakerShop.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final JwtProperty jwtProperty;

    public JwtUtil(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    public String generateToken(MyUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer(jwtProperty.getJwtIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperty.getJwtLifetime().toMillis()))
                .claim("phone", userDetails.getPhoneNumber())
                .claim("email", userDetails.getUsername())
                .claim("role", userDetails.getRole().name())
                .signWith(SignatureAlgorithm.HS512, jwtProperty.getJwtSecret())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = getUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public String getUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String getUserPhone(String token) {
        return extractAllClaims(token).get("phone", String.class);
    }

    public String getUserEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    public String getUserRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperty.getJwtSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody();
    }
}
