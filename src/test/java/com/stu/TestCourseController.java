package com.stu;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.stu.dao.CourseRespository;
import com.stu.dao.CourseService;
import com.stu.model.CourseBean;
import com.stu.model.StudentBean;



 

@SpringBootTest
@AutoConfigureMockMvc
public class TestCourseController {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	CourseService courseService;
	
	@MockBean
	CourseRespository repo;
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  
	@Test
	public void testsetupaddcourse() throws Exception {
		List<CourseBean> courseList=new ArrayList<>();
		when(courseService.getAllCourses()).thenReturn(courseList);
		this.mockMvc.perform(get("/setupaddcourse"))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"))
		.andExpect(model().attribute("courseBean",Matchers.notNullValue()))
		.andExpect(model().attribute("list", Matchers.is(courseList)));
	}
	
	 
	@Test
	public void testAddCourseValidate() throws Exception {
	    CourseBean courseBean = new CourseBean();
	    List<StudentBean> stuList = new ArrayList<>();
	    courseBean.setName("exampleName"); // Set the name consistently
	    courseBean.setStudentList(stuList);
	    courseBean.setId("COU001");
	    when(courseService.isCourseNameUnique(courseBean.getName())).thenReturn(true);

	    this.mockMvc.perform(MockMvcRequestBuilders.post("/addcourse"))
	            .andExpect(status().is(200))
	            .andExpect(view().name("BUD003"));

	    when(courseService.save(courseBean)).thenReturn(courseBean);
	    this.mockMvc.perform(post("/addcourse").flashAttr("courseBean", courseBean))
	            .andExpect(status().is(302))
	            .andExpect(redirectedUrl("/setupaddcourse"));
	} 

	@Test
    void testDisplayView() throws Exception {
        // Arrange
        List<CourseBean> activeCourseList = new ArrayList<>();
        when(courseService.getAllActiveCourses()).thenReturn(activeCourseList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/showcourse"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("BUD003"))
                .andExpect(MockMvcResultMatchers.model().attribute("list", Matchers.is(activeCourseList)));
    }

	
	
}
