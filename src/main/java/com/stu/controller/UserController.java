package com.stu.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.stu.dao.UserService;
import com.stu.model.StudentBean;
import com.stu.model.UserBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {
	@Autowired 
	private UserService userService;
	
	@GetMapping("/") 
    public ModelAndView showLoginForm(Model model,HttpSession session) {
		String registeredName = (String) session.getAttribute("registeredName");
		UserBean userBean = new UserBean();
//		if (registeredName != null) {
//            userBean.setUserName(registeredName);
//        } 
		  
		session.setAttribute("userBean",userBean);
		return new ModelAndView ("LGN001","userBean", userBean);
    }  
	  
    @PostMapping("/login")
    public String login(@ModelAttribute("userBean") @Validated UserBean userBean, ModelMap model, BindingResult bs,HttpServletRequest request) {
//        if (bs.hasErrors()) {
//            return "LGN001";
//        }
         
        HttpSession session = request.getSession();
        UserBean user = userService.findByUserNameAndUserPass(userBean.getUserName(),userBean.getUserPass());
        if (user != null) {
            if ("Inactive".equals(user.getAction())) {
            	model.addAttribute("inactiveError", "Inactivated user");               
                System.out.println("Non-existing account: "+user.getAction());
                return "LGN001";
            } else {
                session.setAttribute("loginName", user.getUserName());
                session.setAttribute("loginId", user.getUserId());
                return "Admin".equals(user.getUserRole()) ? "MNU001" : "MNU001.1";
            }
        } else { 
            model.addAttribute("error", "Please check your data again.");
            return "LGN001";
        }
    }
    
    @GetMapping("/registerlink")
    public ModelAndView displayRegisterForm() {
        UserBean userBean = new UserBean();
        // Set the initial value for the 'action' property
        userBean.setAction("Active");

        String generatedUserId = userService.generateUserId();
        userBean.setUserId(generatedUserId);

        ModelAndView modelAndView = new ModelAndView("USR001-1");
        modelAndView.addObject("userBean", userBean); // Add userBean to the model

        return modelAndView; 
    } 

    @PostMapping("/register")
    public String register(@ModelAttribute("userBean") @Validated UserBean userBean, ModelMap model, BindingResult bs) {
//        if (bs.hasErrors()) {
//            return "USR001-1";
//        }
        
        List<UserBean> existingUsers = userService.findByUserEmail(userBean.getUserEmail());
//        if (!existingUsers.isEmpty()) {
//            model.addAttribute("emailError", "Email is already registered");
//            return "USR001-1";
//        }
	     
        userService.registerUser(userBean);
        return "LGN001";
    }
    
    @GetMapping("/setupuserbyadmin")
    public ModelAndView displayRegForm() {
    	UserBean userBean = new UserBean();
        // Set the initial value for the 'action' property
    	userBean.setAction("Active");
    	
    	String generatedUserId = userService.generateUserId();
        userBean.setUserId(generatedUserId);
        return new ModelAndView("USR001","userBean",userBean);
    }

    @PostMapping("/registerbyadmin")
    public String registerbyadmin(@ModelAttribute("userBean") @Validated UserBean userBean, ModelMap model, BindingResult bs) {
        if (bs.hasErrors()) {
            return "USR001";
        }
        List<UserBean> existingUsers = userService.findByUserEmail(userBean.getUserEmail());
        if (!existingUsers.isEmpty()) {
            model.addAttribute("emailError", "Email is already registered");
            return "USR001";
        }
        userService.registerUser(userBean);
        return "redirect:/showuser";
    }
    //check email duplicate in controller  
    
    @GetMapping("/showuser")
	public String displayView(ModelMap model) {
    	List<UserBean> list = userService.getAllActiveUsers();
		model.addAttribute("list", list);
		return "USR003";
	}
    
    @GetMapping("/setupupdateuser")
	public ModelAndView setupUpdateUser(@RequestParam("id") String userId) {
    	UserBean userBean = userService.getUsersByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id: " + userId));
        return new ModelAndView("USR002", "userBean", userBean);
	}
			
	
	@PostMapping("/updateuser")
	public String updateuser(@ModelAttribute("userBean")@Validated UserBean bean, BindingResult bs, ModelMap model) {
		if(bs.hasErrors()) {
		return "USR002";
	}
		
		userService.update(bean, bean.getUserId());
		return "redirect:/showuser";
	}
	
	
	@GetMapping(value="/deleteuser")
	public String deleteUser(@RequestParam ("id") String userId,ModelMap model,HttpServletRequest request) {
		int res = userService.updateUserAction(userId, "Inactive");
	    HttpSession session = request.getSession();
	    String loggedInUserId = (String) session.getAttribute("loginId");
	    if (res > 0) {
	        // Check if the ID being deleted is the same as the logged-in user's ID
	        if (userId.equals(loggedInUserId)) {
	            // If they match, invalidate the session and redirect to the home page        	
	        	session.invalidate();
	            return "redirect:/";
	        } else { 

	            return "redirect:/showuser";
	        }
	    } else {
	        model.addAttribute("error", "Delete Failed");
	        return "redirect:/showuser";
	    } 
	} 
	 
	
	@GetMapping(value = "/searchuser")
	public String searchUser(@RequestParam(name = "search", required = false) String search,
	                            @RequestParam(name = "action", defaultValue = "Active") String action,
	                            ModelMap model) {

	    List<UserBean> searchResult;

	    if (search != null && !search.isEmpty()) {
	        searchResult = userService.getBySearch(search);
	    } else {
	  
	       searchResult = userService.getByAction(action);
	    }

	    if (searchResult.isEmpty()) {
	        model.addAttribute("msg", "Not found.");
	    }

	    model.addAttribute("list", searchResult);

	    return "searchuser"; 
	}
	@GetMapping(value="/getdata")
	@ResponseBody
	@CrossOrigin
	public List<UserBean> getData() {
	    // Call your service method to fetch the data from the database
	    List<UserBean> userList = userService.getAllActiveUsers();

	    return userList;
	}
//	public List<UserBean> getData(){
//		//return"{\"message\":\"Hello FETCH\"};
//		UserBean myobj=new UserBean();
//		myobj.setUserId("1");
//		myobj.setUserName("Eh Eh");
//		myobj.setUserEmail("eheh@gmail.com");
//		myobj.setUserPass("1234");
//		myobj.setUserRole("Admin");
//		UserBean myobj1=new UserBean();
//		myobj1.setUserId("2");
//		myobj1.setUserName("Htet Myat");
//		myobj1.setUserEmail("htetmyat@gmail.com");
//		myobj1.setUserPass("1234");
//		myobj1.setUserRole("User");
//		List<UserBean> list=new ArrayList<>();
//		list.add(myobj);
//		list.add(myobj1);
//		return list;
//	}
	@PostMapping(value = "/postdata", consumes = "application/json")
	@ResponseBody
	@CrossOrigin
	public ResponseEntity<String> postData(@RequestBody List<UserBean> userBeans) {
	    for (UserBean userBean : userBeans) {
	        userService.save(userBean);
	    }
	    return ResponseEntity.ok("Data received and saved successfully");
	}




	
}
