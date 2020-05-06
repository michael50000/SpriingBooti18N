package com.Clover.springboot.AspectInterceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.Clover.springboot.HttpClientInterceptors.RequestResponseLoggingInterceptor;

@Component
public class StudentInterceptor implements HandlerInterceptor,ClientHttpRequestInterceptor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 @Autowired
	 private RequestResponseLoggingInterceptor customInterceptor;
	
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		logger.info("==============================================");
		logger.info("Request is complete");
		logger.info("==============================================");
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
		logger.info("==============================================");
		logger.info("Handler execution is complete");
		logger.info("==============================================");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		logger.info("==============================================");
		logger.info("Before Handler execution"); 
		//ClientHttpRequestExecution cl=new ClientHttpRequestExecution() {
			
			//new ClientHttpResponse();
		
		logger.info("==============================================");
		
		return true;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		// TODO Auto-generated method stub
		logger.info("#######################################################");
		logger.info("#######################################################");
		logger.info("#######################################################");
		loggerRequest(request,body);
		ClientHttpResponse response = execution.execute(request, body);
		loggerResponse(response);
		response.getHeaders().add("headerName", "VALUE");
		return null;
	}
	
	private void loggerResponse(ClientHttpResponse response) throws IOException {
		// TODO Auto-generated method stub
		logger.info("****is Debug enable*****"+logger.isDebugEnabled());
		logger.info("****is Debug enable*****"+logger.isDebugEnabled());
		logger.info("****is Debug enable*****"+logger.isDebugEnabled());
		if (logger.isDebugEnabled()) 
        {
            logger.debug("============================response begin==========================================");
            logger.debug("Status code  : {}", response.getStatusCode());
            logger.debug("Status text  : {}", response.getStatusText());
            logger.debug("Headers      : {}", response.getHeaders());
            logger.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            logger.debug("=======================response end=================================================");
        }
	}

	private void loggerRequest(HttpRequest request, byte[] body) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) 
        {
            logger.debug("===========================request begin================================================");
            logger.debug("URI         : {}", request.getURI());
            logger.debug("Method      : {}", request.getMethod());
            logger.debug("Headers     : {}", request.getHeaders());
            logger.debug("Request body: {}", new String(body, "UTF-8"));
            logger.debug("==========================request end================================================");
        }
		
	}
	
	

	
	
	

}
