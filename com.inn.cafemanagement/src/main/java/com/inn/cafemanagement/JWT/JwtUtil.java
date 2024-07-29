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
	
	private String secretKey = "secret";
	
	
	public String extractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return  extractClaims(token, Claims::getExpiration);
	}
	
	public <T> T extractClaims(String token, Function <Claims,T> claimResolver) {
		final Claims claims = extractAllClaims(token) ;
		return claimResolver.apply(claims);
	}
	
	public Claims extractAllClaims(String token) {
		
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public  String generateToken(String userName,String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		return createToken(claims, userName);
	}
	
	private String createToken(Map<String, Object> claims,String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*10*10))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
		}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}
}
