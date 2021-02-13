package com.app.security.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtils {

		public static String getJwtTokenHeader() {
			return "Authorization";
		}
		
		public static String getJwtTokenPrefix() {
				return "Bearer";
		}
		
		
		public static Boolean isValidJwt(String token, String secret) {
				Boolean result = Boolean.TRUE;
				try {
					 Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
				}catch(Exception e) {
					result = Boolean.FALSE;
				}finally {
					
				}
				return result;
		}
		
		public static String createSimpleJwtToken(String username, String secret, Long expiredToken) {
			 Claims clms = Jwts.claims().setSubject(username);
			 final long now = System.currentTimeMillis();
			String token = Jwts.builder()
	                .setClaims(clms)
	                .setIssuedAt(new Date(now))
	                .setExpiration(new Date(now + expiredToken * 1000))
	                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
	                .compact();
			return token;
		}
		
		
		public static void permitThis() {
			List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
            list.add(new SimpleGrantedAuthority("ROLE_USER"));
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("permit",null,list);
            SecurityContextHolder.getContext().setAuthentication(auth);
            
			
		}
		
	
		public static void main(String args[]) {
			String secret = "patarku ganteng";
			String token   = createSimpleJwtToken("patartimotius", secret, 1000l);
			System.out.println("token:"+token);
			System.out.println(isValidJwt("2", "ga"));
			System.out.println("token="+isValidJwt(token+"1",secret));
			
		}
}
