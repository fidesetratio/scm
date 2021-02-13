package com.app.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.rest.request.RequestLogin;
import com.app.security.jwt.JwtTokenUtils;

@RestController
public class LoginRestController {

	
	private static final Logger logger = LoggerFactory.getLogger(LoginRestController.class);
	
	@Autowired
	private Environment env;
	

	@Autowired
	private AuthenticationManager manager;
		
	@RequestMapping(value="/login", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResultToken> login(@RequestBody RequestLogin login){
		logger.info("requestLogin:"+login.getUsername() + ","+login.getPassword());
		  ResultToken token = new ResultToken();
		  
	      Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
		  Boolean authenticated = true;
	      try {
	       manager.authenticate(auth);
		}catch(Exception e) {
			authenticated = false;
		}
		
	       if(authenticated) {
	    	   token.setMsg("success");
	    	   token.setToken(JwtTokenUtils.createSimpleJwtToken(login.getUsername(), env.getProperty("secret"),1000L));
	       }
		 ResponseEntity<ResultToken> response  = new ResponseEntity<ResultToken>(token,HttpStatus.OK);
		 return response;
	}
	
	class ResultToken{
		private String token;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		private String msg;
		
	}

}
