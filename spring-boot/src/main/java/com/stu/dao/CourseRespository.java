package com.stu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.stu.model.CourseBean;



public interface CourseRespository extends CrudRepository<CourseBean, String> {
	@Query("SELECT COUNT(c) FROM CourseBean c WHERE c.name = :courseName")
    int countByName(@Param("courseName") String courseName);
	//method in courseRespository
	//repository github
	@Query("SELECT COUNT(c) + 1 FROM CourseBean c")
    Long getNextCourseId();
	
	@Query("SELECT c FROM CourseBean c WHERE c.action = :action")
    List<CourseBean> findAllActiveCourses(@Param("action") String action);
	
	@Modifying
    @Query("UPDATE CourseBean c SET c.action = :action WHERE c.id = :id")
    int updateCourseAction(@Param("id") String id, @Param("action") String action);
}
