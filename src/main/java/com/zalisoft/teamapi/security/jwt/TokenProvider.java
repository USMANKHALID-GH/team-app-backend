package com.zalisoft.teamapi.security.jwt;

import com.zalisoft.teamapi.constant.SecurityConstants;
import com.zalisoft.teamapi.dto.AuthToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
public class TokenProvider  implements InitializingBean {

    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000L;

    private Key key;
    @Value("${jwt.secret_key}")
    private String secret;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public AuthToken createToken(Authentication authentication) {
        List<String> privileges = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String authorities = privileges.stream().collect(Collectors.joining(","));

        Date validity = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY);

        String jwtTokenString = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(SecurityConstants.AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity).compact();
        return new AuthToken(jwtTokenString, validity.getTime(), privileges);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {

        if (token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            token = token.substring(7);
        }
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities;
        String authKey = claims.get(SecurityConstants.AUTHORITIES_KEY).toString();
        if (StringUtils.isNotEmpty(authKey)) {
            authorities = Arrays.stream(claims.get(SecurityConstants.AUTHORITIES_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } else {
            authorities = new ArrayList<>();
        }

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {

        if (authToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            authToken = authToken.substring(7);
        }
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
        } catch (ExpiredJwtException e) {
        } catch (UnsupportedJwtException e) {

        } catch (IllegalArgumentException e) {

        }
        return false;
    }
}
