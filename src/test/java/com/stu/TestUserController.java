package com.stu;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.stu.controller.UserController;
import com.stu.dao.UserRespository;
import com.stu.dao.UserService;
import com.stu.model.UserBean;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {
	@Autowired
	private MockMvc mockMvc;
	 @InjectMocks
	    private UserController userController;
	@MockBean
	UserService userService;
	@MockBean
	UserRespository userRepo;
	 @Mock
	    private HttpSession session;
	@BeforeEach()
	void setUp() {
		MockitoAnnotations.openMocks(this);
		
	}
	 @Test
	    public void testShowLoginForm() throws Exception {
	     UserBean user=new UserBean();
	     
	     user.setUserName("Eh Eh");
	     when(session.getAttribute("registeredName")).thenReturn(user);
		 mockMvc.perform(get("/"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("LGN001"))
	                .andExpect(model().attributeExists("userBean"))
	                .andExpect(model().attribute("userBean", hasProperty("userName", nullValue())))
	                .andReturn();
	    }
	 @Test
	    public void testLoginValidUser() throws Exception {
	        // Mocking user retrieval
	        UserBean mockUser = new UserBean();
	        mockUser.setAction("Active");
	        mockUser.setUserName("validUser");
	        mockUser.setUserId("userId");
	        mockUser.setUserRole("User");

	        when(userService.findByUserNameAndUserPass(anyString(), anyString())).thenReturn(mockUser);

	        // Mocking session
	        when(session.getAttribute("loginName")).thenReturn("existingName");
	        when(session.getAttribute("loginId")).thenReturn("existingId");

	        // Performing the login request
	        mockMvc.perform(post("/login")
	                .param("userName", "validUser")
	                .param("userPass", "validPassword"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("MNU001"))
	                .andExpect(model().attributeExists("loginName", "loginId"));
	    }

	    @Test
	    public void testLoginInactiveUser() throws Exception {
	        // Mocking user retrieval for an inactive user
	        UserBean mockInactiveUser = new UserBean();
	        mockInactiveUser.setAction("Inactive");

	        when(userService.findByUserNameAndUserPass(anyString(), anyString())).thenReturn(mockInactiveUser);

	        // Performing the login request
	        mockMvc.perform(post("/login")
	                .param("userName", "inactiveUser")
	                .param("userPass", "validPassword"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("LGN001"))
	                .andExpect(model().attributeExists("inactiveError"));

	        
	    }

	
	 @Test
	    public void testDisplayRegisterForm() {
	        // Mock the userService behavior
	        when(userService.generateUserId()).thenReturn("mockedUserId");

	        // Call the method
	        ModelAndView modelAndView = userController.displayRegisterForm();

	        // Verify that the userService method was called
	        verify(userService, times(1)).generateUserId();

	        // Verify the ModelAndView and its contents
	        assertEquals("USR001-1", modelAndView.getViewName());

	        UserBean userBean = (UserBean) modelAndView.getModel().get("userBean");
	        assertNotNull(userBean);
	        assertEquals("Active", userBean.getAction());
	        assertEquals("mockedUserId", userBean.getUserId());
	    } 
	 @Test
	 public void testRegister() throws Exception{
		 UserBean user=new UserBean();
		 user.setUserEmail("eheh@gmail.com");
		 when(userService.findByUserEmail("eheh@gmail.com")).thenReturn(Collections.emptyList());
		
		 mockMvc.perform(post("/register")
		 .flashAttr("user",user)
		 .param("userEmail","eheh@gmail.com"))
		 .andExpect(status().isOk())
		 .andExpect(view().name("LGN001"))
		 .andExpect(model().attributeExists("user"))
		 ;
		 
	 }
	 
	 @Test
	    public void testDeleteUserSuccess() throws Exception {
	        // Mock the behavior of userService.updateUserAction
	        when(userService.updateUserAction("validUserId", "Inactive")).thenReturn(1);

	        HttpSession session = mockMvc.perform(get("/deleteuser")
	                .param("id", "validUserId"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("redirect:/showuser"))
	                .andExpect(model().attributeDoesNotExist("error"))
	                .andReturn().getRequest().getSession();

	        // Verify that the session is invalidated for the logged-in user
	        assertNull(session.getAttribute("loginId"));
	    }

	    @Test
	    public void testDeleteUserFailure() throws Exception {
	        // Mock the behavior of userService.updateUserAction for a failure scenario
	        when(userService.updateUserAction("invalidUserId", "Inactive")).thenReturn(0);

	        mockMvc.perform(get("/deleteuser")
	                .param("id", "invalidUserId"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("redirect:/showuser"))
	                .andExpect(model().attribute("error", "Delete Failed"));
	    }
	    
	    @Test
	    public void testSearchUser() throws Exception {
	        // Mock the behavior of userService.getBySearch and userService.getByAction
	        when(userService.getBySearch("test")).thenReturn(Collections.singletonList(new UserBean()));
	        when(userService.getByAction("Active")).thenReturn(Collections.singletonList(new UserBean()));

	        // Test without parameters
	        mockMvc.perform(get("/searchuser"))
	            .andExpect(status().isOk())
	            .andExpect(view().name("searchuser"))
	            .andExpect(model().attributeExists("list"))
	            .andExpect(model().attribute("list", hasSize(greaterThanOrEqualTo(0))))
	            .andExpect(model().attributeDoesNotExist("msg"));

	        // Test with search parameter
	        mockMvc.perform(get("/searchuser").param("search", "test"))
	            .andExpect(status().isOk())
	            .andExpect(view().name("searchuser"))
	            .andExpect(model().attributeExists("list"))
	            .andExpect(model().attribute("list", hasSize(greaterThanOrEqualTo(0))))
	            .andExpect(model().attributeDoesNotExist("msg"));

	        // Test when searchResult is empty
	        when(userService.getBySearch("empty")).thenReturn(Collections.emptyList());
	        mockMvc.perform(get("/searchuser").param("search", "empty"))
	            .andExpect(status().isOk())
	            .andExpect(view().name("searchuser"))
	            .andExpect(model().attributeExists("list"))
	            .andExpect(model().attribute("list", hasSize(0)))
	            .andExpect(model().attribute("msg", "Not found."));
	    }
	    
}
