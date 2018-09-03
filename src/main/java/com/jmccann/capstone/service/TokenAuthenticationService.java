package com.jmccann.capstone.service;

import com.jmccann.capstone.domain.User;
import com.jmccann.capstone.repository.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.parser.DateParser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse res, User user) {
        System.out.println("test");
        Random x = new Random();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        String JWT = Jwts.builder()
                .setId(new UUID(x.nextLong(), x.nextLong()).toString())
                .setSubject(String.format("{" +
                        "\"id\": \"%s\"," +
                        "\"username\": \"%s\"," +
                        "\"password\": \"%s\"," +
                        "\"email\": \"%s\"," +
                        "\"joinDate\": \"%s\"," +
                        "\"enabled\": %s," +
                        "\"role\": \"%s\"" +
                        "}", user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), df.format(user.getJoinDate()), user.getEnabled(), user.getRole()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }

        return null;
    }
}
