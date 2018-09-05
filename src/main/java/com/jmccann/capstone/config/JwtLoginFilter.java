package com.jmccann.capstone.config;

import com.jmccann.capstone.domain.User;
import com.jmccann.capstone.repository.UserRepo;
import com.jmccann.capstone.service.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final UserRepo userRepo;

    JwtLoginFilter(AuthenticationManager authManager, UserRepo repo) {
        super(new AntPathRequestMatcher("/login"));
        setAuthenticationManager(authManager);
        this.userRepo = repo;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException {
        User creds = new ObjectMapper()
                .readValue(req.getInputStream(), User.class);
        GrantedAuthority authority = (GrantedAuthority) creds::getRole;
        Collection<GrantedAuthority> authorityCollection = new ArrayList<>();
        authorityCollection.add(authority);


        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        authorityCollection
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName());
        TokenAuthenticationService
                .addAuthentication(res, user);
    }
}
