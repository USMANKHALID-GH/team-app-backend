package com.zalisoft.teamapi.util;

import com.zalisoft.teamapi.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Component
public class SecurityUtils {


    public static String getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Optional<String> username = Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetails) {
                        UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                        return springSecurityUser.getUsername();
                    } else if (authentication.getPrincipal() instanceof String) {
                        return (String) authentication.getPrincipal();
                    }
                    return null;
                });
        return username.orElse(null);
    }

    public static boolean hasAuthority(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
                .orElse(false);
    }

    public static void userIdControl(Long headerUserId, Long userId) {
        if (!userId.equals(headerUserId)) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "Size ait olmayan kullanıcı ile işlem yapamazsınız.");
        }
    }


}
