package com.stu.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.model.CourseBean;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CourseService {
	@Autowired
	private CourseRespository courseRepo;

	public void save(CourseBean bean) {
		courseRepo.save(bean);
	}
	
	public List<CourseBean> getAllCourses()
	{
		List<CourseBean> list = (List<CourseBean>) courseRepo.findAll();
		return list;
	}
	
	public CourseBean getCourseById(String courseId) {
        return courseRepo.findById(courseId).orElse(null);
    }
	
	public String generateCourseId() {
        Long count = courseRepo.getNextCourseId();
        return String.format("COU%03d", count);
    }
	
	public boolean isCourseNameUnique(String courseName) {
        return courseRepo.countByName(courseName) == 0;
    }
	//method in courseService
	
	public List<CourseBean> getAllActiveCourses() {
        return courseRepo.findAllActiveCourses("Active");
    }
	
	public int updateCourseAction(String id, String action) {
        return courseRepo.updateCourseAction(id, action);
    }

}
