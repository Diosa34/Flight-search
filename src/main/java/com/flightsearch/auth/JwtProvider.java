//package com.flightsearch.auth;
//
//import com.flightsearch.models.User;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import lombok.NonNull;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//
//@Slf4j
//@Component
//public class JwtProvider {
//
//    public String generateAccessToken(@NonNull User user) {
//        final LocalDateTime now = LocalDateTime.now();
//        final Instant accessExpirationInstant = now.plusDays(7).atZone(ZoneId.systemDefault()).toInstant();
//        final Date accessExpiration = Date.from(accessExpirationInstant);
//        return Jwts.builder()
//                .setSubject(user.getLogin())
//                .setExpiration(accessExpiration)
////                .claim("roles", user.getRoles())
//                .claim("id", user.getId())
//                .compact();
//    }
//
//    boolean validateToken(@NonNull String token) {
//        try {
//            Jwts.parserBuilder()
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException expEx) {
//            log.error("Token expired", expEx);
//        } catch (UnsupportedJwtException unsEx) {
//            log.error("Unsupported jwt", unsEx);
//        } catch (MalformedJwtException mjEx) {
//            log.error("Malformed jwt", mjEx);
//        } catch (SignatureException sEx) {
//            log.error("Invalid signature", sEx);
//        } catch (Exception e) {
//            log.error("invalid token", e);
//        }
//        return false;
//    }
//
//    Claims getClaims(@NonNull String token) {
//        return Jwts.parserBuilder()
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//}
