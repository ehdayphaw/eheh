package com.stu.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class CourseBean {
	@Id
	private String id;
	private String name;
	
	
	
	@ManyToMany(mappedBy="attend")
	private List<StudentBean> studentList;
	
	public List<StudentBean> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<StudentBean> studentList) {
		this.studentList = studentList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setId(CourseBean courseBean) {
		
		
	}
	
	private String action;
}
