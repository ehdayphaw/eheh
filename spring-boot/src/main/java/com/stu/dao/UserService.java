package com.stu.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stu.model.StudentBean;
import com.stu.model.UserBean;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRespository userRespository;
	
	/*public boolean validateUserCredentials(String userName, String userPass) {
        UserBean user = userRespository.findByUserNameAndUserPass(userName, userPass);
        return user != null;
    }*/

    public boolean registerUser(UserBean userBean) {
        userRespository.save(userBean);
        return true;
    }
    
    
    public List<UserBean> getAllActiveUsers() {
        return userRespository.findAllActiveUsers("Active");
    }
    
    public Optional<UserBean> getUsersByUserId(String userId) {
		return userRespository.findById(userId);
	}
	//saving a specific record by using the method save() of CrudRepository
	public void save(UserBean users)
	{
		userRespository.save(users);
	}
	//deleting a specific record by using the method deleteById() of CrudRepository
	public void delete(String userId)
	{
		userRespository.deleteById(userId);
	}
	//updating a record
	public void update(UserBean users,String userId)
	{
		userRespository.save(users);
	}
	
	
	public UserBean findByUserNameAndUserPass(String userName, String userPass) {
        return userRespository.findByUserNameAndUserPass(userName, userPass);
    }
	
	public List<UserBean> findByUserEmail(String userEmail) {
        return userRespository.findByUserEmail(userEmail);
    }
	//methods in userService
	
	public int updateUserAction(String userId, String action) {
        return userRespository.updateUserAction(userId, action);
    }
	//methods in userService
	
	public List<UserBean> getBySearch(String search){
		return userRespository.findBySearch(search);
	}
	
	public List<UserBean> getByAction(String action) {
	    return userRespository.findByAction(action);
	}
	
	public String generateUserId() {
        Long count = userRespository.getNextUserId();
        return String.format("USR%03d", count);
    }
	/*
	 * public Page<UserBean> getAllActiveUsers(int page, int pageSize) { Pageable
	 * pageable = PageRequest.of(page, pageSize); return
	 * userRespository.findAllActiveUsers("Active", pageable); }
	 */
}
