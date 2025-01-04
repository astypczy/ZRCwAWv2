package com.pwr.project.config.auth;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.pwr.project.entities.User;
import com.pwr.project.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class CognitoJwtAuthenticationFilter extends OncePerRequestFilter {

    private final ConfigurableJWTProcessor jwtProcessor;
    private final UserRepository userRepository;

    public CognitoJwtAuthenticationFilter(ConfigurableJWTProcessor jwtProcessor, UserRepository userRepository) {
        this.jwtProcessor = jwtProcessor;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            try {
                JWTClaimsSet claims = jwtProcessor.process(jwt, null);
                String cognitoSub = claims.getSubject();

                if (cognitoSub != null) {
                    // For sync-user endpoint, always allow with basic authority
                    if (request.getRequestURI().contains("/api/auth/sync-user")) {
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                            cognitoSub, null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        // For other endpoints, check user in database
                        userRepository.findByCognitoSub(cognitoSub).ifPresent(user -> {
                            List<SimpleGrantedAuthority> authorities = List.of(
                                new SimpleGrantedAuthority(user.getIsSeller() ? "ROLE_SELLER" : "ROLE_USER")
                            );
                            Authentication authentication = new UsernamePasswordAuthenticationToken(
                                cognitoSub, null, authorities
                            );
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        });
                    }
                }
            } catch (Exception e) {
                logger.error("Error processing JWT token", e);
            }
        }

        filterChain.doFilter(request, response);
    }
}