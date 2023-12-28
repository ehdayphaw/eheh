package com.stu;

import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import com.stu.dao.CourseService;
import com.stu.dao.StudentRespository;
import com.stu.dao.StudentService;
import com.stu.model.CourseBean;
import com.stu.model.StudentBean;

@SpringBootTest
@AutoConfigureMockMvc
public class TestStudentController {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	StudentService stuService;
	@MockBean
	StudentRespository stdRepo;
	@MockBean
	CourseService courseService;
	 @Test
	    public void testDeleteStudentSuccess() throws Exception {
	        // Mock the behavior of stuService.updateStudentAction
	        when(stuService.updateStudentAction("validStudentId", "Inactive")).thenReturn(1);

	        mockMvc.perform(MockMvcRequestBuilders.get("/deletestudent")
	                .param("id", "validStudentId"))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())  // Expecting a redirect
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/showstudent"))
	                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("error"));
	    }
	   

	    @Test
	    public void testDeleteStudentFailure() throws Exception {
	        // Mock the behavior of stuService.updateStudentAction for a failure scenario
	        when(stuService.updateStudentAction("invalidStudentId", "Inactive")).thenReturn(0);

	        mockMvc.perform(MockMvcRequestBuilders.get("/deletestudent")
	                .param("id", "invalidStudentId"))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())  // Expecting a redirect
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/showstudent"))
	                ;
	    }
	    @Test
	    public void testSetupUpdateStudent() throws Exception {
	       
	        StudentBean studentBean = new StudentBean(); // Create a test student bean
	        when(stuService.getStudentsByStudentId("testStudentId")).thenReturn(java.util.Optional.of(studentBean));

	        
	        List<CourseBean> courseList = new ArrayList<>(); // Add some test data
	        when(courseService.getAllActiveCourses()).thenReturn(courseList);

	        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/setupupdatestudent")
	                .param("id", "testStudentId"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.view().name("STU002"))
	                .andReturn();
	        ModelMap modelMap = result.getModelAndView().getModelMap();
	       
	    }
	    @Test
	    public void testDisplayView() throws Exception {
	        // Mock the behavior of stuService.getAllActiveStudents
	        List<StudentBean> studentList = new ArrayList<>(); // Add some test data
	        when(stuService.getAllActiveStudents()).thenReturn(studentList);

	        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/showstudent"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.view().name("STU003"))
	                .andReturn();

	        // Verify the model attribute
	        ModelMap modelMap = result.getModelAndView().getModelMap();
	        List<StudentBean> actualStudentList = (List<StudentBean>) modelMap.get("list");
	    }
}
