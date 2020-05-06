package com.Clover.springboot.HttpClientInterceptors;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;



@Component
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor  {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		// TODO Auto-generated method stub
		log.info("#######################################################");
		log.info("#######################################################");
		log.info("#######################################################");
		logRequest(request,body);
		ClientHttpResponse response = execution.execute(request, body);
		logResponse(response);
		response.getHeaders().add("headerName", "VALUE");
		return response;
	}

	private void logResponse(ClientHttpResponse response) throws IOException {
		// TODO Auto-generated method stub
		log.info("****is Debug enable*****"+log.isDebugEnabled());
		log.info("****is Debug enable*****"+log.isDebugEnabled());
		log.info("****is Debug enable*****"+log.isDebugEnabled());
		if (log.isDebugEnabled()) 
        {
            log.debug("============================response begin==========================================");
            log.debug("Status code  : {}", response.getStatusCode());
            log.debug("Status text  : {}", response.getStatusText());
            log.debug("Headers      : {}", response.getHeaders());
            log.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            log.debug("=======================response end=================================================");
        }
	}

	private void logRequest(HttpRequest request, byte[] body) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		if (log.isDebugEnabled()) 
        {
            log.debug("===========================request begin================================================");
            log.debug("URI         : {}", request.getURI());
            log.debug("Method      : {}", request.getMethod());
            log.debug("Headers     : {}", request.getHeaders());
            log.debug("Request body: {}", new String(body, "UTF-8"));
            log.debug("==========================request end================================================");
        }
		
	}

}
