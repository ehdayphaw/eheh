package com.stu.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;


@Entity
public class StudentBean {
	@Id
	private String studentId;
	private String studentName;
	private String studentPhone;
	private String edu;
	private String dob;
	private String gender;
	private String  photo;
	private String action;
	
	
	@ManyToMany(cascade = {CascadeType.MERGE})
	@JoinTable(
	    name = "student_course",
	    joinColumns = @JoinColumn(name = "stu_id"),
	    inverseJoinColumns = @JoinColumn(name = "id")
	)
	private List<CourseBean> attend;
	
	private List<String> attendlist;
    
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getEdu() { 
		 return edu; 
	} 
	
	public void setEdu(String edu) {
	  this.edu = edu; 
	}
	 
	public String getStudentPhone() {
		return studentPhone;
	}

	public void setStudentPhone(String studentPhone) {
		this.studentPhone = studentPhone;
	}

	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public StudentBean() {
		super();
		
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<CourseBean> getAttend() {
		return attend;
	}

	public void setAttend(List<CourseBean> attend) {
		this.attend = attend;
	}
	
	
	  public List<String> getAttendlist() { 
		  return attendlist; 
	  }
	  
	  public void setAttendlist(List<String> attendlist) { 
		  this.attendlist =attendlist; 
	 }
	 
}
