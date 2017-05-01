package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.interceptor.SessionInterceptor;
@Component
public class webconfiguration extends WebMvcConfigurerAdapter{
    @Autowired
	SessionInterceptor sessionInterceptor; 
   
    
    
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor);
		super.addInterceptors(registry);
	}	
}
