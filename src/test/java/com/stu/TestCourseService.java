package com.stu;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.stu.dao.CourseRespository;
import com.stu.dao.CourseService;
import com.stu.model.CourseBean;
import static org.junit.jupiter.api.Assertions.assertTrue;
import jakarta.validation.constraints.AssertTrue;
 
@SpringBootTest
public class TestCourseService {
	@Mock
	CourseRespository courseRepository;
	
	@InjectMocks
	CourseService courseService;
	 
	@Test
	public void savecourseTest() {
		 CourseBean courseToSave = new CourseBean();
	        courseToSave.setId("COU001");
	        courseToSave.setName("Math");
	        courseToSave.setAction("Active");

	        courseService.save(courseToSave);
	        verify(courseRepository,times(1)).save(courseToSave);
	    }
	
	@Test
	public void getAllCourseTest() {
		List<CourseBean> list=new ArrayList<CourseBean>();
		CourseBean c1=new CourseBean();
		c1.setId("COU001");
		c1.setName("Math");
		c1.setAction("Active");
		CourseBean c2=new CourseBean();
		c2.setId("COU001");
		c2.setName("Math");
		c2.setAction("Active");
		CourseBean c3=new CourseBean();
		c3.setId("COU001");
		c3.setName("Math");
		c3.setAction("Active");
		list.add(c1);
		list.add(c2);
		list.add(c3);
		when(courseRepository.findAll()).thenReturn(list);
		List<CourseBean> courseList=courseService.getAllCourses();
		assertEquals(3,courseList.size());
		verify(courseRepository, times(1)).findAll();
	}
	
	
	@Test
	public void getCourseByIdTest() {

		CourseBean c2=new CourseBean();
		c2.setId("COU001");
		c2.setName("Math");
		c2.setAction("Active");
		
		when(courseRepository.findById("COU001")).thenReturn(java.util.Optional.ofNullable(c2));
		CourseBean getCourse=courseService.getCourseById("COU001");
		assertEquals("COU001", getCourse.getId());
		assertEquals("Math",getCourse.getName());
		assertEquals("Active",getCourse.getAction());
		verify(courseRepository, times(1)).findById("COU001");
	}
	
	   @Test
	    public void generateCourseIdTest() {
	        // Mock the behavior of courseRepo.getNextCourseId
	        when(courseRepository.getNextCourseId()).thenReturn(1L);

	        // Call the method to be tested
	        String result = courseService.generateCourseId();

	        // Assertions
	        assertEquals("COU001", result);

	        // Verify that courseRepo.getNextCourseId was called
	        verify(courseRepository, times(1)).getNextCourseId();
	    }
	   @Test
	   public void getcourseNameUniqueTest() {
		   when(courseRepository.countByName("Math")).thenReturn((int) 0L); // Assuming "Math" is a unique name

	        // Call the method to be tested
	        boolean result = courseService.isCourseNameUnique("Math");

	        // Assertions
	       assertTrue(result); 

	        // Verify that courseRepo.countByName was called with the correct parameter
	        verify(courseRepository, times(1)).countByName("Math");
	   }
	   
	   @Test
	    public void testGetAllActiveCourses() {
	        // Mock data
	        CourseBean course1 = new CourseBean();
	        course1.setId("COU001");
	        course1.setName("Math");
	        course1.setAction("Active");
	        CourseBean course2 = new CourseBean();
	        course1.setId("COU002");
	        course1.setName("Science");
	        course1.setAction("Active");
	        List<CourseBean> mockCourses = Arrays.asList(course1, course2);

	        // Stub the behavior of the courseRepo.findAllActiveCourses method
	        when(courseRepository.findAllActiveCourses("Active")).thenReturn(mockCourses);

	        // Call the method to be tested
	        List<CourseBean> result = courseService.getAllActiveCourses();

	        // Assertions
	        assertEquals(2, result.size());
	        // Add more assertions based on your specific requirements
	    }

	    @Test
	    public void testUpdateCourseAction() {
	        // Mock data
	        String courseId = "COU001";
	        String newAction = "Inactive";

	        // Stub the behavior of the courseRepo.updateCourseAction method
	        when(courseRepository.updateCourseAction(courseId, newAction)).thenReturn(1);

	        // Call the method to be tested
	        int result = courseService.updateCourseAction(courseId, newAction);

	        // Assertions
	        assertEquals(1, result);
	        // Add more assertions based on your specific requirements
	    }
}
