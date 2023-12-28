package com.stu.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.model.StudentBean;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class StudentService {
	@Autowired
	private StudentRespository stuRespository;

    public boolean registerStudent(StudentBean studentBean) {
    	stuRespository.save(studentBean);
        return true;
    }
    
    public List<StudentBean> getAllActiveStudents() {
        return stuRespository.findAllActiveStudents("Active");
    }
    
    public Optional<StudentBean> getStudentsByStudentId(String userId) {
		return stuRespository.findById(userId);
	}
	//saving a specific record by using the method save() of CrudRepository
	public void save(StudentBean users)
	{
		stuRespository.save(users);
	}
	//deleting a specific record by using the method deleteById() of CrudRepository
	public void delete(String userId)
	{
		stuRespository.deleteById(userId);
	}
	//updating a record
	public void update(StudentBean users,String userId)
	{
		stuRespository.save(users);
	}
	
	
	public List<StudentBean> getBySearch(String search){
		return stuRespository.findBySearch(search);
	}
	
	public List<StudentBean> getByAction(String action) {
	    return stuRespository.findByAction(action);
	}
	public int updateStudentAction(String studentId, String action) {
        return stuRespository.updateStudentAction(studentId, action);
    }
	
	public String generateStudentId() {
        Long count = stuRespository.getNextStudentId();
        return String.format("STU%03d", count);
    }
	
	
}
