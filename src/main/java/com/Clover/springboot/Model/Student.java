package com.Clover.springboot.Model;
// default package
// Generated 24 Mar, 2020 12:56:18 PM by Hibernate Tools 5.2.3.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Student generated by hbm2java
 */
@Entity
@Table(name = "student", catalog = "sys")
public class Student implements java.io.Serializable {

	private Integer sid;
	private String name;
	private String email;
	private String dept;

	public Student() {
	}

	public Student(String name, String email, String dept) {
		this.name = name;
		this.email = email;
		this.dept = dept;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "sid", unique = true, nullable = false)
	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	@Column(name = "name", nullable = false, length = 25)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email", nullable = false, length = 25)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "dept", nullable = false, length = 25)
	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

}
