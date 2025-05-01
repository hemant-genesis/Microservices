package com.user.service.implementations;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.user.entity.Role;
import com.user.entity.User;
import com.user.service.abstractions.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// @Service
// public class JwtServiceImpl implements JwtService{
//     private final String SECRET_KEY = "your-secret-key";

//     @Override
//     public String generateToken(User user) {
//         if (user == null || user.getEmail() == null || user.getRoles() == null) {
//             throw new IllegalArgumentException("User, email, and roles must not be null");
//         }
//         return Jwts.builder()
//                 .setSubject(user.getEmail())
//                 .claim("roles", user.getRoles().stream().map(Role::getRoleName).toList())
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
//                 .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                 .compact();
//     }

//     @Override
//     public String extractUsername(String token) {
//         return Jwts.parser()
//                 .setSigningKey(SECRET_KEY)
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getSubject();
//     }

//     @Override
//     public boolean validateToken(String token, UserDetails userDetails) {
//         String username = extractUsername(token);
//         return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//     }

//     private boolean isTokenExpired(String token) {
//         return Jwts.parser()
//                 .setSigningKey(SECRET_KEY)
//                 .parseClaimsJws(token)
//                 .getBody()
//                 .getExpiration()
//                 .before(new Date());
//     }
// }
