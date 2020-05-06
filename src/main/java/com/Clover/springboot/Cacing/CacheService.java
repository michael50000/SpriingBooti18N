package com.Clover.springboot.Cacing;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Clover.springboot.ExceptionHandler.StudentNotFoundException;
import com.Clover.springboot.HibernateUtil.HibernateUtil;
import com.Clover.springboot.Model.Student;
import com.Clover.springboot.Model.Workers;


@Service
public class CacheService {
	Logger logger = LoggerFactory.getLogger(CacheService.class);
	@Autowired   
	private MessageSource messageSource;
	
	@Cacheable("createWorkers")
	public Workers createWorkers(String sid) {
		// TODO Auto-generated method stub
		
		try {
			logger.info("Going to sleep for five seconds");
			Thread.sleep(1000*5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Workers(sid,"Sajal" ,"V");
	}

	//@Cacheable("getDatabaseCaching")
	public List<Student> getDatabaseCaching(Integer sid,Locale locale) {
		// TODO Auto-generated method stub
		List<Student> st=new ArrayList<Student>();
		long start = System.currentTimeMillis();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	           Query q= session.createQuery("select s from Student s where s.sid = :sid");
	           q.setParameter("sid", sid);
	           st=q.list();
	           if(st!=null && st.size()<=0){
	        	   String message=messageSource.getMessage("good.morning.message", null, locale);
	        	   throw new StudentNotFoundException(message);
	           }
	            
	         }
		long end = System.currentTimeMillis();
		float sec = (end - start) / 1000F; 
		logger.info("Time taken to complete"+sec+"Seconds");
		return st;

  }
	
	public ResponseEntity<Student> getInter(Integer sid) {
		// TODO Auto-generated method stub
		List<Student> st=new ArrayList<Student>();
		long start = System.currentTimeMillis();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	           Query q= session.createQuery("select s from Student s where s.sid = :sid");
	           q.setParameter("sid", sid);
	           st=q.list();
	           if(st!=null && st.size()<=0){
	        	   throw new StudentNotFoundException("Student: "+sid+ " Not Found" );
	           }
	            
	         }
		long end = System.currentTimeMillis();
		float sec = (end - start) / 1000F; 
		logger.info("Time taken to complete"+sec+"Seconds");
		return (ResponseEntity<Student>) st;

  }

	public void createStudentNotFoundException(StudentNotFoundException stx,String foo) {
		logger.info("createStudentNotFoundException"+stx.getClass().getName());
		
	}

	public void getDatabaseI18n(Integer sid) {
		// TODO Auto-generated method stub
		
	}
}
