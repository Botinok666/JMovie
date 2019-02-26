package com.sfedu.JMovie.api.security;

import com.sfedu.JMovie.db.RoleType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtils {
    public SecurityContextUtils() {
    }
//
//    public static boolean isLoggedIn() {
//        Authentication authentication = authentication();
//        if (authentication == null) {
//            return false;
//        }
//        return authentication.isAuthenticated();
//    }
//
    public static boolean hasRole(RoleType type) {
        Authentication authentication = authentication();
        if (authentication == null) {
            return false;
        }
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(type.name());
        return authentication.getAuthorities().contains(grantedAuthority);
    }

    public static SecurityUserDetails getUser() {
        Authentication authentication = authentication();
        if (authentication == null) {
            return null;
        }
        return (SecurityUserDetails)authentication.getPrincipal();
    }

//    public static RoleType getRole() {
//        Authentication authentication = authentication();
//        if (authentication == null) {
//            return null;
//        }
//        return authentication.getAuthorities().stream()
//                .map(x -> RoleType.findByType(x.getAuthority()))
//                .findFirst()
//                .orElseThrow(() -> new EnumConstantNotPresentException(RoleType.class, "Unknown"));
//    }

    private static Authentication authentication() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            throw new SecurityContextException("SecurityContext not found");
        }

        final Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            throw new SecurityContextException("SecurityContext authentication not found");
        }
        return authentication;
    }
}
