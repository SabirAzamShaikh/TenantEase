package com.example.TenantEase.jwt;

import com.example.TenantEase.service.impl.MyUserDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil util;
    private final MyUserDetailService userDetailService;

    public JwtAuthenticationFilter(JwtUtil util, MyUserDetailService userDetailService) {
        this.util = util;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        String username = util.extractUsername(token);
        Claims claims = util.extractAllClaims(token);
        List<String> authorities = claims.get("authorities", List.class);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Optional DB lookup to verify user still exists / not disabled
            UserDetails userDetails = userDetailService.loadUserByUsername(username);

            if (util.validateToken(token, userDetails.getUsername())) {
                var grantedAuthorities = authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();
                 log.info("THe Granted Authorities Taken From Token {}",grantedAuthorities);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}