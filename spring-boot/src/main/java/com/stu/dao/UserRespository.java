package com.stu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.stu.model.UserBean;

public interface UserRespository extends CrudRepository<UserBean, String> {
    UserBean findByUserNameAndUserPass(String userName, String userPass);
       
    List<UserBean> findByUserEmail(String userEmail);
    //method in userRespository
    
    List<UserBean> findByAction(String action);
	
	@Query(value = "SELECT * FROM user_bean u WHERE (u.user_id LIKE CONCAT('%', :search, '%') OR u.user_name LIKE CONCAT('%', :search, '%')) AND u.action = 'Active'", nativeQuery = true)
	List<UserBean> findBySearch(@Param("search") String search);
    
    @Modifying
    @Query("UPDATE UserBean u SET u.action = :action WHERE u.userId = :userId")
    int updateUserAction(@Param("userId") String userId, @Param("action") String action);
    //method in userRespository
    
    @Query("SELECT u FROM UserBean u WHERE u.action = :action")
    List<UserBean> findAllActiveUsers(@Param("action") String action);
    
    @Query("SELECT COUNT(u) + 1 FROM UserBean u")
    Long getNextUserId();
    
    
}
