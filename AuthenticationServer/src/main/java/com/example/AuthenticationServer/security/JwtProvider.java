package com.example.AuthenticationServer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    @Value("$security.jwt.token.key")
    private String key;

    public String createToken(AuthUserDetail userDetails){
        Claims claims = Jwts.claims().setSubject(userDetails.getUserId().toString());

        claims.put("permissions", userDetails.getAuthorities());
        claims.put("email", userDetails.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Optional<AuthUserDetail> resolveToken(HttpServletRequest request){
        String prefixedToken = request.getHeader("Authorization"); // extract
        String token = prefixedToken.substring(7);

        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody(); // decode

        int userId = Integer.valueOf(claims.getSubject());
        List<LinkedHashMap<String, String>> permissions = (List<LinkedHashMap<String, String>>) claims.get("permissions");

        List<GrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.get("authority")))
                .collect(Collectors.toList());

        String email = (String) claims.get("email");


        return Optional.of(AuthUserDetail.builder()
                .authorities(authorities)
                .userId(userId)
                .email(email)
                .build());
    }

}
