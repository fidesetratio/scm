package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.filter.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	  @Autowired
	  private  RestAuthenticationEntryPoint entryPoint;
	  
	  @Autowired
	    private CustomAuthenticationProvider authProvider;

	  @Autowired 
	  private JwtTokenFilter jwtTokenFilter;
	   

		
		
	  
	  	@Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    	auth.authenticationProvider(authProvider);
	    }
	    
	  	
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.cors().and()
	                .csrf().disable().authorizeRequests().
	                			antMatchers("/login/**").permitAll().
	                			antMatchers("/test/**").hasRole("USER").
	                	//		antMatchers("/hidden/user").hasRole("USER").
	                	//		antMatchers("/api-vega/**").hasRole("USER").
		                		anyRequest().authenticated().and()               
	                    .exceptionHandling().authenticationEntryPoint(entryPoint).and()   
		                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
	        
	        http.addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	

	    }


		@Override
		@Bean
		protected AuthenticationManager authenticationManager() throws Exception {
			// TODO Auto-generated method stub
			return super.authenticationManager();
		};

	   
}
