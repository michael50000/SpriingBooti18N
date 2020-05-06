package com.Clover.springboot.HttpClientInterceptors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	 @Autowired
	 private RequestResponseLoggingInterceptor customInterceptor;
	 
	 private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/*@Bean
    public RestTemplate restTemplate(){
        RestTemplate template = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        template.add(customInterceptor);
        return template;
    }*/
	
	/*@Bean
	public RestTemplate createRestTemplate(RequestResponseLoggingInterceptor loggingInterceptor) {
	    
	  RestTemplate restTemplate = new RestTemplate();
	  restTemplate.setInterceptors(Collections.singletonList(customInterceptor));
	    
	  return restTemplate;
	}
	*/
	/*@Bean
	public RestTemplate restTemplate() 
	{
	    RestTemplate restTemplate = new RestTemplate();
	 
	    restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(clientHttpRequestFactory()));
	    restTemplate.setMessageConverters(Cosllections.singletonList(mappingJacksonHttpMessageConverter()));
	 
	    restTemplate.setInterceptors( Collections.singletonList(customInterceptor) );
	 
	 
	    return restTemplate;
	}
*/
	 
	/* @Bean
	 public RestTemplate createRestTemplate(RequestResponseLoggingInterceptor loggingInterceptor) {
	     
	   RestTemplate restTemplate = new RestTemplate();
	   restTemplate.setInterceptors(Collections.singletonList(loggingInterceptor));
	     
	   return restTemplate;
	   
	 }*/
	 
	/* @Bean
	 public RestTemplate getfxoWsClientRestTemplate(){
	     RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
	     restTemplate.setInterceptors(Collections.singletonList(customInterceptor));
	     return  restTemplate;
	 }
*/
	 
	 @Bean
	 public RestTemplate restTemplate() {
	     RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
	     logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	     logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	     logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
     
	     List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
	     if (CollectionUtils.isEmpty(interceptors)) {
	         interceptors = new ArrayList<>();
	     }

	     interceptors.add(new RequestResponseLoggingInterceptor());
	     restTemplate.setInterceptors(interceptors);
	     logger.info("^^^^^^^^^^^^^^^l^^^^^^^^^^^^^^^^^^^^^^^^^");
	     logger.info("^^^^^^^^^^^^"+interceptors.size()+"^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+logger.isDebugEnabled());
	     logger.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	     return restTemplate;
	 }
	 
	
	
	
}
