package com.example.funtime_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailsService userDetailsService;
    public String generateToken(String username) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("funn_app")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignKey())
                .claim("roles", userDetails.getAuthorities())
                .compact();

    }

    private Key getSignKey() {
        byte[] decode = Decoders.BASE64.decode("1234567891011131123456789101113112345678910111311234567891011131");
        return Keys.hmacShaKeyFor(decode);
    }

    public boolean isValid(String token) {
        Claims claims = getClaims(token);
        return true;
    }

    public String getUsername(String token){

        Claims claims = getClaims(token);
        return claims.getSubject();

    }
    public List<GrantedAuthority> grantedAuthorities(String token){
        Claims claims = getClaims(token);

//        String roles = claims.get("roles", String.class);
//        System.out.println();
//        String[] split = roles.split(",");
//        List<SimpleGrantedAuthority> list = Arrays.stream(split).map(SimpleGrantedAuthority::new).toList();
//        return list;

        List<LinkedHashMap<String, Object>> rolesMap = (List<LinkedHashMap<String, Object>>) claims.get("roles");
        List<String> roles = rolesMap.stream()
                .map(roleMap -> (String) roleMap.get("authority"))
                .toList();
        List<GrantedAuthority> collect = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        System.out.println(collect);
        return collect;
    }
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
