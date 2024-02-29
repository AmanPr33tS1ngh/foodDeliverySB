package com.foodDeliveryApp.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtProvider {
    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication auth){
        Collection<? extends GrantedAuthority> grantedAuthorities = auth.getAuthorities();
        String roles = populateAuthorities(grantedAuthorities);
        String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", auth.getName()).claim("authorities", auth.getAuthorities()).signWith(key)
                .compact();
        return jwt;
    }
    public String getEmailFromJwtToken(String jwt){
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(jwt).getBody();
        return String.valueOf(claims.get("email"));
    }
    public String populateAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities){
        Set<String> auths = new HashSet<>();
        for(GrantedAuthority authority: grantedAuthorities){
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }

}
