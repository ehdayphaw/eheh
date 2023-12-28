package com.stu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.stu.model.StudentBean;



public interface StudentRespository extends CrudRepository<StudentBean, String> {
	List<StudentBean> findByAction(String action);
	
	@Query(value = "SELECT * FROM student_bean s WHERE (s.student_id LIKE CONCAT('%', :search, '%') OR s.student_name LIKE CONCAT('%', :search, '%')) AND s.action = 'Active'", nativeQuery = true)
	List<StudentBean> findBySearch(@Param("search") String search);
    
    @Query("SELECT s FROM StudentBean s WHERE s.action = :action")
    List<StudentBean> findAllActiveStudents(@Param("action") String action);
    
    @Modifying
    @Query("UPDATE StudentBean s SET s.action = :action WHERE s.studentId = :studentId")
    int updateStudentAction(@Param("studentId") String studentId, @Param("action") String action);
    
    @Query("SELECT COUNT(s) + 1 FROM StudentBean s")
    Long getNextStudentId();
    
    
}
