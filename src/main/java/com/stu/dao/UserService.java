package com.stu.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stu.model.StudentBean;
import com.stu.model.UserBean;

import jakarta.transaction.Transactional;

@Service 
@Transactional
public class UserService {
	@Autowired 
	private UserRespository userRepo;

    public boolean registerUser(UserBean userBean) {
    	userRepo.save(userBean);
        return true;
    } 
    
	public void update(UserBean users,String userId)
	{
		userRepo.save(users);
	}
	
	
	public UserBean findByUserNameAndUserPass(String userName, String userPass) {
        return userRepo.findByUserNameAndUserPass(userName, userPass);
    }
	
	public List<UserBean> findByUserEmail(String userEmail) {
        return userRepo.findByUserEmail(userEmail);
    }
	
	public int updateUserAction(String userId, String action) {
        return userRepo.updateUserAction(userId, action);
    }
	
	public List<UserBean> getAllActiveUsers() {
	        return userRepo.findAllActiveUsers("Active");
	}
	    
	public Optional<UserBean> getUsersByUserId(String userId) {
			return userRepo.findById(userId);
	}
		
	public void save(UserBean users)
	{
			userRepo.save(users);
	}
	public void delete(String userId) 
	{
			userRepo.deleteById(userId);
	}
	public List<UserBean> getBySearch(String search){
		return userRepo.findBySearch(search);
	}
	
	public List<UserBean> getByAction(String action) {
	    return userRepo.findByAction(action);
	}
	
	public String generateUserId() {
        Long count = userRepo.getNextUserId();
        return String.format("USR%03d", count);
    }
	

}
