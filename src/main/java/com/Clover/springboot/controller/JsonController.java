package com.Clover.springboot.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Clover.springboot.Cacing.CacheService;
import com.Clover.springboot.ExceptionHandler.StudentNotFoundException;
import com.Clover.springboot.HibernateUtil.HibernateUtil;
import com.Clover.springboot.Model.Student;
import com.Clover.springboot.Model.Workers;
import com.Clover.springboot.Scheduler.ScheduledTasks;
import com.Clover.springboot.Services.I18n;

@RestController
public class JsonController {
	Logger logger = LoggerFactory.getLogger(JsonController.class);
	@Autowired
	private CacheService cs;
	
	//I18n
	@Autowired
	private I18n i18;
	@Autowired   
	private MessageSource messageSource;
	
	
	  
	int count =0;
	
	@RequestMapping(value = "/api/getStudents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Student> getStudents() {
	    
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           return session.createQuery("from Student", Student.class).list();
        }
	}
	
	@RequestMapping("/api/getIntercerptorsStudents")
    public ResponseEntity<List<Student>> getEmployeeById()  {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	           Query q= session.createQuery("select s from Student s");
	           //q.setParameter("sid", sid);
	           List<Student>st=q.list();
	           if(st!=null && st.size()<=0){
	        	   throw new StudentNotFoundException("Student: Not Found" );
	           }
             return ResponseEntity.ok().body(st);
    }
	}

	
	@Retryable(value={StudentNotFoundException.class},maxAttempts=2)
	@RequestMapping(value = "/api/getStudents/{sid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Student> Studentsbyid(@PathVariable(value = "sid") Integer sid) {
	    
	    
		logger.info("Status of the Counter");
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           Query q= session.createQuery("select s from Student s where s.sid = :sid");
           q.setParameter("sid", sid);
           List<Student>st=q.list();
           if(st!=null && st.size()<=0){
        	   throw new StudentNotFoundException("Student: "+sid+ " Not Found" );
           }else{
        	   count=0;
        	   return st;
           }
           
        }
		
	}
	
	@Recover
	public void recoverStudentNotFoundException(StudentNotFoundException stx,String foo){
		//cs.createStudentNotFoundException(stx);
		logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		logger.info(stx.toString());
	}
	
	
	
	/*@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") String id) {
	   //repo.removeById(id);
	return new ResponseEntity<>("Your message here", HttpStatus.OK);
	    }
	*/
	
	@RequestMapping(value = "/api/getCachingData/{sid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Workers getCachingStudents(@PathVariable(value = "sid") String sid) {
		System.out.println("Inside json controller"+sid);
		return cs.createWorkers(sid);
	    
		/*try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           return session.createQuery("from Student", Student.class).list();
        }*/
	}
	
	@RequestMapping(value = "/api/getDatabaseCaching/{sid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Student> getStudentsbyCaching(@PathVariable(value = "sid") Integer sid,
			@RequestHeader(value="Accept-Language") Locale locale) {
		logger.info("getDatabaseCaching");
		logger.info("________________________________");
		
            return cs.getDatabaseCaching(sid,locale);
        }
	
	
	/*@RequestMapping(value = "/api/getInter/{sid}",produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<Student> getData(@RequestHeader(value="Accept-Language") String acceptLanguage,@PathVariable(value = "sid") Integer sid)
	{
		logger.info("&&&&&&&&&&&&&&"+acceptLanguage);
		//return cs.getInter(sid);
		return messageSource.getMessage("good.morning.message", null, acceptLanguage);
	}*/
	
	/*@GetMapping(path="/hello-world-internationalized")*/
	@RequestMapping(value = "/api/hellointernationalized", method = RequestMethod.GET)
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale)  
	{  
	return messageSource.getMessage("good.morning.message", null, locale);  
	}  
	
	/*@RequestMapping(value = "/api/hello-world-internationalized", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE) 
	public List<Student> helloWorldInternationalized(@RequestHeader(name="Accept-Language") Locale locale,@PathVariable(value = "sid") Integer sid) { 
		
		logger.info("________________________________");
		logger.info("________________________________");
	    //return messageSource.getMessage("good.morning.message", null, locale);  //i18
		return i18.getDatabaseI18n(sid,locale);
	 }*/
}

//Query q = getSession().createQuery("select u from User u where u.company.id = :id");
//q.setParameter("id", id);
//List<User> list = q.list();
