package com.stu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.stu.dao.CourseService;
import com.stu .model.CourseBean;

@Controller
public class CourseController {
	@Autowired
	private CourseService courseService;
	 
	@GetMapping("/setupaddcourse") 
	public ModelAndView setupaddbook(ModelMap model) {
		CourseBean courseBean = new CourseBean();
        // Set the initial value for the 'action' property
        courseBean.setAction("Active");
        
        String generatedCourseId = courseService.generateCourseId();
        courseBean.setId(generatedCourseId);
		
		/*
		 * List<CourseBean> list = courseService.getAllActiveCourses();
		 * model.addAttribute("list", list);
		 */
        List<CourseBean> list=courseService.getAllCourses();
		model.addAttribute("list", list);
        return new ModelAndView("BUD003", "courseBean", courseBean);
	}
	
	@PostMapping("/addcourse")
    public String addCourse(@ModelAttribute("courseBean") @Validated CourseBean bean, BindingResult bs, ModelMap model) {
//        if (bs.hasErrors()) {
//            return "redirect:/setupaddcourse";
//        }

        // Check if the course name is unique
        if (!courseService.isCourseNameUnique(bean.getName())) {
            model.addAttribute("nameError", "Course name already exists");
            List<CourseBean> list=courseService.getAllCourses();
    		model.addAttribute("list", list);
            return "BUD003";
            
        }

        // Save the course if the name is unique
        courseService.save(bean);
        return "redirect:/setupaddcourse";
            
    } 
	
	@GetMapping("/showcourse")
	 public String showCourses(ModelMap model) {
        List<CourseBean> courseList = courseService.getAllActiveCourses();
        model.addAttribute("list", courseList);

        // Add a new CourseBean to the model
        model.addAttribute("courseBean", new CourseBean());

        return "BUD003"; // Assuming "BUD003" is your view name
    }
	

}
