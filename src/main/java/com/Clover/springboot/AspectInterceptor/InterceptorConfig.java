package com.Clover.springboot.AspectInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.Clover.springboot.HttpClientInterceptors.RequestResponseLoggingInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private StudentInterceptor stincp;
	 @Autowired
	 private RequestResponseLoggingInterceptor customInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(stincp);
		//registry.addInterceptor(customInterceptor);
	}

}
