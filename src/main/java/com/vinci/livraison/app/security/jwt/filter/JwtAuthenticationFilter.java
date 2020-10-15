package com.vinci.livraison.app.security.jwt.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinci.livraison.app.security.authentications.AuthTokenFactory;
import com.vinci.livraison.app.exceptionhandler.ApiErrorResponse;
import com.vinci.livraison.app.security.jwt.JwtService;
import com.vinci.livraison.app.security.jwt.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Set<String> publicPaths = new HashSet<>(Arrays.asList("api/*/login", "api/*/login/exists"));
    private AntPathMatcher matcher = new AntPathMatcher();

    private ObjectMapper mapper;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return publicPaths.stream().anyMatch(s -> matcher.match(s, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtService.getTokenFromRequest(req);

        if (token == null) {
            filterChain.doFilter(req, res);
            return;
        }

        req.setAttribute("token", token);

        try {

            Authentication authentication = attemptAuthentication(req, res);

            successfulAuthentication(req, res, filterChain, authentication);

        } catch (AuthenticationException e) {
            unsuccessfulAuthentication(req, res, e);
        }

    }

    protected Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {

        String tokenStr = (String) req.getAttribute("token");
        req.removeAttribute("token");

        Token token = jwtService.parseAccessTokenOrfails(tokenStr);


        Authentication authentication = AuthTokenFactory.getAuthentication(token.getUserType(), token.getUserId());

        return authenticationManager.authenticate(authentication);
    }

    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(req, res);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        SecurityContextHolder.clearContext();

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        mapper.writeValue(
                response.getWriter(),
                new ApiErrorResponse(
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                        failed.getMessage()
                )
        );

    }


}
