package com.stu;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.stu.dao.UserRespository;
import com.stu.dao.UserService;
import com.stu.model.UserBean;

import jakarta.validation.constraints.AssertTrue;
@SpringBootTest
public class TestUserService {
		@Autowired
	    private UserService userService;

	    @MockBean
	    private UserRespository userRepository;
 
	    @Test 
	    public void testRegisterUser() {
	        // Mock data
	        UserBean user = new UserBean();
	        user.setUserId("USR001");
	        user.setUserName("john_doe");
	        user.setUserPass("password123");
	        user.setUserEmail("john.doe@example.com");

	        // Stub the behavior of the userRepository.save method
	        when(userRepository.save(user)).thenReturn(user);

	        // Call the method to be tested
	        boolean result = userService.registerUser(user);

	        // Assertions
	        assertEquals(true, result);
	       
	    }

	    @Test
	    public void testUpdate() {
	        // Mock data
	        UserBean user = new UserBean();
	        user.setUserId("USR001");
	        user.setUserName("john_doe");
	        user.setUserPass("password123");
	        user.setUserEmail("john.doe@example.com");

	        // Call the method to be tested
	        userService.update(user, "USR001");

	        // Verify that the userRepository.save method was called with the correct arguments
	        verify(userRepository, times(1)).save(user);
	       
	    }
	    
	    @Test
	    public void testFindByUserNameAndUserPass() {
	        // Mock data
	        String userName = "john_doe";
	        String userPass = "password123";
	        UserBean user = new UserBean();
	        user.setUserId("USR001");
	        user.setUserName("john_doe");
	        user.setUserPass("password123");
	        user.setUserEmail("john.doe@example.com");
	        // Stub the behavior of userRepository.findByUserNameAndUserPass
	        when(userRepository.findByUserNameAndUserPass(userName, userPass)).thenReturn(user);

	        // Call the method to be tested
	        UserBean result = userService.findByUserNameAndUserPass(userName, userPass);

	        // Assertions
	        assertThat(result).isNotNull();
	        assertThat(result.getUserName()).isEqualTo(userName);
	        assertThat(result.getUserPass()).isEqualTo(userPass);
	       
	    }
	    @Test
	    public void testFindByUserEmailTest() {
	    	String email="eheh@gmail.com";
	    	List<UserBean> users =new ArrayList<UserBean>();
	        UserBean searchuser=new UserBean();
	        searchuser.setUserId("USR001");
	        searchuser.setUserName("Eh Eh Hlaing");
	        searchuser.setUserPass("eheh123");
	        searchuser.setUserEmail("eheh@gmail.com");
	        UserBean searchuser1=new UserBean();
	        searchuser1.setUserId("USR002");
	        searchuser1.setUserName("Eh Eh");
	        searchuser1.setUserPass("eheh123");
	        searchuser1.setUserEmail("eheh1@gmail.com");
	        users.add(searchuser);
	        users.add(searchuser1);
		     when(userRepository.findByUserEmail(email)).thenReturn(users);
		     List<UserBean> result=userService.findByUserEmail(email);
		     assertThat(result).hasSize(2);
	    }
	    @Test
	    public void testUpdateUserAction() {
	        // Mock data
	        String userId = "USR001";
	        String action = "Activate";
	        
	        // Stub the behavior of userRepository.updateUserAction
	        when(userRepository.updateUserAction(userId, action)).thenReturn(1);

	        // Call the method to be tested
	        int result = userService.updateUserAction(userId, action);

	        // Assertions
	        verify(userRepository, times(1)).updateUserAction(userId, action);
	        assertThat(result).isEqualTo(1);
	    }
	    @Test
	    public void testgetAllActiveUsersTest() {
	    	List<UserBean> activeUsers =new ArrayList<UserBean>();
	        UserBean searchuser=new UserBean();
	        searchuser.setUserId("USR001");
	        searchuser.setUserName("Eh Eh Hlaing");
	        searchuser.setUserPass("eheh1234");
	        searchuser.setUserEmail("eheh@gmail.com");
	        searchuser.setAction("Active");
	        UserBean searchuser1=new UserBean();
	        searchuser1.setUserId("USR002");
	        searchuser1.setUserName("Eh Eh");
	        searchuser1.setUserPass("eheh123");
	        searchuser1.setUserEmail("eheh1@gmail.com");
	        searchuser1.setAction("Active");
	        activeUsers.add(searchuser);
	        activeUsers.add(searchuser1); 
	        when(userRepository.findAllActiveUsers("Active")).thenReturn(activeUsers);
	        List<UserBean> result = userService.getAllActiveUsers();
	        // Assertions
	        assertEquals(activeUsers.size(), result.size());
	        for (int i = 0; i < activeUsers.size(); i++) {
	            assertEquals(activeUsers.get(i), result.get(i));
	        }
	        // Verify that userRepo.findAllActiveUsers was called once with the correct argument
	        verify(userRepository, times(1)).findAllActiveUsers("Active");

	    }
	    @Test
	    public void testGetUsersByUserId() {
	        // Mock data
	        String userId = "USR001";
	        UserBean user = new UserBean();
	        user.setUserId(userId);
	        user.setUserName("John Doe");
	        user.setUserPass("password123");
	        user.setUserEmail("john.doe@example.com");

	        // Stub the behavior of userRepo.findById
	        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

	        // Call the method to be tested
	        Optional<UserBean> result = userService.getUsersByUserId(userId);

	        // Assertions
	        AssertTrue(result.isPresent());
	        assertEquals(userId, result.get().getUserId());
	        assertEquals("John Doe", result.get().getUserName());
	        assertEquals("password123", result.get().getUserPass());
	        assertEquals("john.doe@example.com", result.get().getUserEmail());

	        // Verify that userRepo.findById was called once with the correct argument
	        verify(userRepository, times(1)).findById(userId);
	    }
	   

		private void AssertTrue(boolean present) {
			
			
		}

		@Test
	    public void saveTest() {
	    	UserBean setUser=new UserBean();
	    	setUser.setUserId("USR001");
	    	setUser.setUserName("Eh Eh");
	    	setUser.setUserPass("eheh123");
	    	setUser.setUserEmail("eheh@gmail.com");
	    	userService.save(setUser);
	    	verify(userRepository,times(1)).save(setUser);
	    }
	    
	    @Test
	    public void deleteTest() {
	    	userService.delete("USR001");
	    	verify(userRepository,times(1)).deleteById("USR001");
	    }
	    
	    @Test
	    public void testGetBySearch() {
	        // Mock data
	        String search = "Eh Eh";
	        List<UserBean> users =new ArrayList<UserBean>();
	        UserBean searchuser=new UserBean();
	        searchuser.setUserId("USR001");
	        searchuser.setUserName("Eh Eh Hlaing");
	        searchuser.setUserPass("eheh123");
	        searchuser.setUserEmail("eheh@gmail.com");
	        UserBean searchuser1=new UserBean();
	        searchuser1.setUserId("USR002");
	        searchuser1.setUserName("Eh Eh");
	        searchuser1.setUserPass("eheh123");
	        searchuser1.setUserEmail("eheh@gmail.com");
	        users.add(searchuser);
	        users.add(searchuser1);

	        // Stub the behavior of userRepository.findBySearch
	        when(userRepository.findBySearch(search)).thenReturn(users);

	        // Call the method to be tested
	        List<UserBean> result = userService.getBySearch(search);

	        // Assertions
	        assertThat(result).hasSize(2);
	       
	    }

	    @Test
	    public void testGetByAction() {
	        // Mock data
	        String action = "Active";
	        List<UserBean> users =new ArrayList<UserBean>();
	        UserBean searchuser=new UserBean();
	        searchuser.setUserId("USR001");
	        searchuser.setUserName("Eh Eh Hlaing");
	        searchuser.setUserPass("eheh123");
	        searchuser.setUserEmail("eheh@gmail.com");
	        UserBean searchuser1=new UserBean();
	        searchuser1.setUserId("USR002");
	        searchuser1.setUserName("Eh Eh");
	        searchuser1.setUserPass("eheh123");
	        searchuser1.setUserEmail("eheh@gmail.com");
	        users.add(searchuser);
	        users.add(searchuser1);

	        // Stub the behavior of userRepository.findByAction
	        when(userRepository.findByAction(action)).thenReturn(users);

	        // Call the method to be tested
	        List<UserBean> result = userService.getByAction(action);

	        // Assertions
	        assertThat(result).hasSize(2);
	      
	    } 
	    
	    @Test
	    public void generateUserIdTest() {
	    	when(userRepository.getNextUserId()).thenReturn(1L);
	    	String result=userService.generateUserId();
	    	assertEquals("USR001",result);
	    	verify(userRepository,times(1)).getNextUserId();
	    }
}
