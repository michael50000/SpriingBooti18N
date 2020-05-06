package com.Clover.springboot.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.Clover.springboot.ExceptionHandler.StudentNotFoundException;
import com.Clover.springboot.HibernateUtil.HibernateUtil;
import com.Clover.springboot.Model.Student;
import com.Clover.springboot.controller.JsonController;

@Component
public class I18n {
	@Autowired   
	private MessageSource messageSource;
	Logger logger = LoggerFactory.getLogger(I18n.class);
	//@Cacheable("getDatabaseCaching")
	    public List<Student> getDatabaseI18n(Integer sid, Locale locale) {
		// TODO Auto-generated method stub
		List<Student> st=new ArrayList<Student>();
		long start = System.currentTimeMillis();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	           Query q= session.createQuery("select s from Student s where s.sid = :sid");
	           q.setParameter("sid", sid);
	           st=q.list();
	           if(st!=null && st.size()<=0){
	        	   String msg=messageSource.getMessage("good.morning.message", null, locale);
	        	   throw new StudentNotFoundException(msg);
	           }
	            
	         }
		long end = System.currentTimeMillis();
		float sec = (end - start) / 1000F; 
		logger.info("Time taken to complete"+sec+"Seconds");
		return st;

  }

}
