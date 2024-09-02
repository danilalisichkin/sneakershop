package com.sneakershop.SneakerShop.security.jwt;

import com.google.common.net.HttpHeaders;
import com.sneakershop.SneakerShop.security.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtUtil jwtUtil;
    private final MyUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, MyUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        logger.info("JWT Authentication Filter works");
        logger.info("Http Request URI: {}", request.getRequestURI());
        logger.info("Http request body: {}", request.getQueryString());
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
            logger.warn("JWT Authentication Filter works without Bearer header");
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        final var username = jwtUtil.getUserName(token);

        if (!StringUtils.isEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("JWT Authentication Filter works with username {}", username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.isTokenValid(token, userDetails)) {
                logger.info("JWT Authentication Filter works with valid token");
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
            logger.info("JWT Authentication Filter works with invalid token");
        }
        chain.doFilter(request, response);
    }
}
