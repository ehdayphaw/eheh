package com.stu.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView displayLoginForm(Model model,HttpSession session) {
		String registeredName = (String) session.getAttribute("registeredName");
		UserBean userBean = new UserBean();
		if (registeredName != null) {
            userBean.setUserName(registeredName);
        }
		session.setAttribute("userBean",userBean);
		return new ModelAndView ("LGN001","userBean", userBean);
    }
	
    @PostMapping("/login")
    public String login(@ModelAttribute("userBean") @Validated UserBean userBean, ModelMap model, BindingResult bs,HttpServletRequest request) {
        if (bs.hasErrors()) {
            return "LGN001";
        }
        
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
        
        return new ModelAndView("USR001-1","userBean",userBean);
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userBean") @Validated UserBean userBean, ModelMap model, BindingResult bs) {
        if (bs.hasErrors()) {
            return "USR001-1";
        }
        
        List<UserBean> existingUsers = userService.findByUserEmail(userBean.getUserEmail());
        if (!existingUsers.isEmpty()) {
            model.addAttribute("emailError", "Email is already registered");
            return "USR001-1";
        }
	    
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
	            // If they don't match, delete the user and redirect to the "show user" page
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
	        // If a search term is provided, search with the term
	        searchResult = userService.getBySearch(search);
	    } else {
	        // If no search term is provided, show only active students
	        searchResult = userService.getByAction(action);
	    }

	    if (searchResult.isEmpty()) {
	        model.addAttribute("msg", "No results found.");
	    }

	    model.addAttribute("list", searchResult);

	    return "searchuser";
	}
}
