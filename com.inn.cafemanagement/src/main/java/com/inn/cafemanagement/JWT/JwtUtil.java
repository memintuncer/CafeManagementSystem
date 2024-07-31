package com.inn.cafemanagement.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	 private String secret = "btechdays";

	    public String extractUserName(String token){
	        return extractClamis(token , Claims::getSubject);
	    }

	    public Date extractExpiration(String token){
	        return extractClamis(token , Claims::getExpiration);
	    }

	    private <T> T extractClamis(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);

	    }
	    public Claims extractAllClaims(String token) {
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	    }
	    private Boolean isTokenExpired(String token){
	        return extractExpiration(token).before(new Date());
	    }

	    public String generateToken(String Username , String role){
	        Map<String , Object> claims = new HashMap<>();
	        claims.put("role" , role);
	        return createToken(claims, Username);
	    }
	    private String createToken(Map<String , Object> claims , String subject){
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(subject)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
	                .signWith(SignatureAlgorithm.HS256,secret).compact();
	    }
	    public Boolean validateToken(String token , UserDetails userDetails){
	        final String Username = extractUserName(token);
	        return (Username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
	    }
}
