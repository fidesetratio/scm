package com.app.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.security.jwt.JwtTokenUtils;

@Component
public class JwtTokenFilter  extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);
	
	@Autowired
	private Environment env;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		   log.info("jwtTokenFilter..");
		   String token = request.getHeader(JwtTokenUtils.getJwtTokenHeader());
		   if(token != null && token.startsWith(JwtTokenUtils.getJwtTokenPrefix()+" ")) {
	            token = token.replace(JwtTokenUtils.getJwtTokenPrefix() + " ", "");
	            if(JwtTokenUtils.isValidJwt(token,env.getProperty("secret"))) {
	            	log.info("token is valid");
	            	JwtTokenUtils.permitThis();
	            }
	            log.info("token:"+token);
		   }
		 
		   filterChain.doFilter(request, response);
	}

}
