package com.kayipesyaUser.security;


import com.kayipesyaUser.constant.UserRole;
import com.kayipesyaUser.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class TokenManager {
    private static final String secretKey = "LostProperty";
    private static final int validate = 60 * 60 * 1000;
    private UserDetailsService userDetailsService;
    public String generateToken(String email, UserRole userRole) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("auth",new SimpleGrantedAuthority(userRole.getAuthority()));
        Date now = new Date();
        Date validity = new Date(now.getTime() + validate);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256,secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
    public boolean tokenValidate(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Expired or invalid JWT token");
        }
    }
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
    public String getEmailFromToken(String token){
       Claims claim = getClaims(token);
       return claim.getSubject();
    }
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.contains("Bearer")){
            return bearerToken.substring(7);
        }
        return null;

    }
    private static Claims getClaims(String token) {
        Claims claim = Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        return claim;
    }
}
