package com.Clover.springboot.Aspect;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.Clover.springboot.Model.Student;

@Aspect
@Component
public class LoggingAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//@Before("getAllStudents()")
	public void loggingAdvice(JoinPoint joinPoint){
		//System.out.println("Inside logging aspect");
		/*logger.info("Inside logging aspect");
		logger.info(jp.toString());
		logger.info(jp.getTarget().toString());*/
		logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~Before~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		logger.info("Entering in Method :  " + joinPoint.getSignature().getName());
		logger.info("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
		logger.info("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
		logger.info("Target class : " + joinPoint.getTarget().getClass().getName());

		/*if (null != request) {
			logger.debug("Start Header Section of request ");
			logger.debug("Method Type : " + request.getMethod());
			Enumeration headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = (String) headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				logger.debug("Header Name: " + headerName + " Header Value : " + headerValue);
			}
			logger.debug("Request Path info :" + request.getServletPath());
			logger.debug("End Header Section of request ");
		}*/
		
	}
	
	@Pointcut("execution(public * getEmployeeById())")
	public void getAllStudents(){
		
	}
	
	//@Around("getAllStudents()")
	public void loggingAfterAdvice(JoinPoint joinPoint){
		//System.out.println("Inside logging aspect");
		logger.info("Inside AfterAdvice logging aspect");
		logger.info("=================s================");
		
		logger.info("Entering in Method :  " + joinPoint.getSignature().getName());
		logger.info("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
		logger.info("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
		logger.info("Target class : " + joinPoint.getTarget().getClass().getName());
		logger.info("Inside AfterAdvice logging aspect");
		
		
	}
	
	@Around("getAllStudents()")  
    public Object myadvice(ProceedingJoinPoint pjp) throws Throwable   
    {  
        System.out.println("Additional Concern Before calling actual method");  
        Object obj=pjp.proceed();  
        logger.info("=================s================");
		
		logger.info("Entering in Method :  " + pjp.getSignature().getName());
		logger.info("Class Name :  " + pjp.getSignature().getDeclaringTypeName());
		logger.info("Arguments :  " + Arrays.toString(pjp.getArgs()));
		logger.info("Target class : " + pjp.getTarget().getClass().getName());
		logger.info("Inside AfterAdvice logging aspect");
		
        System.out.println("Additional Concern After calling actual method");  
        return obj;  
    }  
	
	
	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controller() {
	}
	//import org.springframework.web.bind.annotation.RestController;
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restController() {
	}
	@Pointcut("execution(* *.*(..))")
	protected void allMethod() {
	}

	@Pointcut("execution(public * *(..))")
	protected void loggingPublicOperation() {
	}

	@Pointcut("execution(* *.*(..))")
	protected void loggingAllOperation() {
	}

	@Pointcut("within(com.Clover.springboot..*)")
	private void logAnyFunctionWithinResource() {
	}
	
	@Before("controller() && restController() && allMethod() && args(..,request)")
	public void logBefore(JoinPoint joinPoint, HttpServletRequest request) {
        logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		logger.debug("Entering in Method :  " + joinPoint.getSignature().getName());
		logger.debug("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
		logger.debug("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
		logger.debug("Target class : " + joinPoint.getTarget().getClass().getName());

		if (null != request) {
			logger.debug("Start Header Section of request ");
			logger.debug("Method Type : " + request.getMethod());
			Enumeration headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = (String) headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				logger.debug("Header Name: " + headerName + " Header Value : " + headerValue);
			}
			logger.debug("Request Path info :" + request.getServletPath());
			logger.debug("End Header Section of request ");
		}
	}
	//After -> All method within resource annotated with @Controller annotation 
	// and return a  value
	@AfterReturning(pointcut = "controller() && allMethod()", returning = "result")
	public void logAfter(JoinPoint joinPoint, Object result) {
		String returnValue = this.getValue(result);
		logger.debug("Method Return value : " + returnValue);
	}
	//After -> Any method within resource annotated with @Controller annotation 
	// throws an exception ...Log it
	@AfterThrowing(pointcut = "controller() && allMethod()", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
		logger.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
		logger.error("Cause : " + exception.getCause());
	}
	//Around -> Any method within resource annotated with @Controller annotation 
	//@Around("getAllStudents()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		
		long start = System.currentTimeMillis();
		try {
			String className = joinPoint.getSignature().getDeclaringTypeName();
			String methodName = joinPoint.getSignature().getName();
			Object result = joinPoint.proceed();
			long elapsedTime = System.currentTimeMillis() - start;
			logger.debug("Method " + className + "." + methodName + " ()" + " execution time : "
					+ elapsedTime + " ms");
		
			return result;
		} catch (IllegalArgumentException e) {
			logger.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
					+ joinPoint.getSignature().getName() + "()");
			throw e;
		}
	}
	private String getValue(Object result) {
		String returnValue = null;
		if (null != result) {
			if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
				returnValue = result.toString();
			} else {
				returnValue = result.toString();
			}
		}
		return returnValue;
	}

	
	
	

}
