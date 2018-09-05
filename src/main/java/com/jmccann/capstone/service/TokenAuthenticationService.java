package com.jmccann.capstone.service;

import com.jmccann.capstone.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {
    static private final long EXPIRATION_TIME = 864000000;
    static private final String KEY = "MySecretKeyIsTheSecretestSecretEver000110!";

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
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
        res.addHeader("Authorization", String.format("Bearer %s", JWT));
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }

        return null;
    }
}
