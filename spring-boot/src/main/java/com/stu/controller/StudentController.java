package com.stu.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.stu.dao.CourseService;
import com.stu.dao.StudentService;
import com.stu.model.CourseBean;
import com.stu.model.StudentBean;
import com.stu.model.UserBean;



@Controller
public class StudentController {
	@Autowired
	private StudentService stuService;
	
	
	@Autowired
	private CourseService courseService;
	
	
	 @GetMapping("/setupstudent")
	    public ModelAndView displayRegForm(ModelMap model) {
	    	StudentBean stuBean = new StudentBean();
	        // Set the initial value for the 'action' property
	    	stuBean.setAction("Active");
	    	
	    	List<CourseBean> list = courseService.getAllActiveCourses();
			model.addAttribute("list", list);
			
			String generatedStudentId = stuService.generateStudentId();
			stuBean.setStudentId(generatedStudentId);
	        
	        return new ModelAndView("STU001","studentBean",stuBean);
	    }
   
	 @PostMapping("/registerstudent")
	 public String registerbyadmin(@RequestParam("inputPhoto") MultipartFile inputPhoto,@ModelAttribute("studentBean") @Validated StudentBean stuBean, ModelMap model, BindingResult bs) throws IllegalStateException, IOException {
		//add photo
		    if (!inputPhoto.isEmpty()) {
	            String originalFileName = inputPhoto.getOriginalFilename();
	            String fileName = originalFileName;
	            String uploadDirectory = "D:\\springBoot_Wordspace\\StudentManagement_springdata_jpa\\src\\main\\resources\\static\\resources\\img\\" + fileName;
	            File destFile = new File(uploadDirectory);
	            inputPhoto.transferTo(destFile);

	            stuBean.setPhoto(fileName);
	        }
		    
	     if (bs.hasErrors()) {
	         return "STU001";
	     }

	     // Set the selected courses in the student bean
	     List<CourseBean> selectedCourses = new ArrayList<>();
	     if (stuBean.getAttendlist() != null) {
	         for (String courseId : stuBean.getAttendlist()) {
	             // Fetch the existing CourseBean from the database
	             CourseBean existingCourse = courseService.getCourseById(courseId);

	             if (existingCourse != null) {
	                 // Preserve the existing course name and action
	                 selectedCourses.add(existingCourse);
	             }
	         }
	     }
	     stuBean.setAttend(selectedCourses);

	     // Call the service method to register the student
	     stuService.registerStudent(stuBean);

	     return "redirect:/showstudent";
	 }
	    
	    @GetMapping("/setupstudentbyuser")
	    public ModelAndView displayRegistrationForm(ModelMap model) {
	    	StudentBean stuBean = new StudentBean();
	        // Set the initial value for the 'action' property
	    	stuBean.setAction("Active");
	    	
	    	List<CourseBean> list = courseService.getAllActiveCourses();
			model.addAttribute("list", list);
			
			String generatedStudentId = stuService.generateStudentId();
			stuBean.setStudentId(generatedStudentId);
			
	        return new ModelAndView("STU001.1","studentBean",stuBean);
	    }

	    @PostMapping("/registerstudentbyuser")
	    public String registerbyuser(@RequestParam("inputPhoto") MultipartFile inputPhoto,@ModelAttribute("studentBean") @Validated StudentBean stuBean, ModelMap model, BindingResult bs) throws IllegalStateException, IOException {
	    	//add photo
		    if (!inputPhoto.isEmpty()) {
	            String originalFileName = inputPhoto.getOriginalFilename();
	            String fileName = originalFileName;
	            String uploadDirectory = "D:\\springBoot_Wordspace\\StudentManagement_springdata_jpa\\src\\main\\resources\\static\\resources\\img\\" + fileName;
	            File destFile = new File(uploadDirectory);
	            inputPhoto.transferTo(destFile);

	            stuBean.setPhoto(fileName);
	        }
	    	 if (bs.hasErrors()) {
		         return "STU001.1";
		     }

		     // Set the selected courses in the student bean
		     List<CourseBean> selectedCourses = new ArrayList<>();
		     if (stuBean.getAttendlist() != null) {
		         for (String courseId : stuBean.getAttendlist()) {
		             // Fetch the existing CourseBean from the database
		             CourseBean existingCourse = courseService.getCourseById(courseId);

		             if (existingCourse != null) {
		                 // Preserve the existing course name and action
		                 selectedCourses.add(existingCourse);
		             }
		         }
		     }
		     stuBean.setAttend(selectedCourses);

		     // Call the service method to register the student
		     stuService.registerStudent(stuBean);

		     return "MNU001.1";
	    	
	    }
	    
	    @GetMapping("/showstudent")
		public String displayView(ModelMap model) {
	    	List<StudentBean> list = stuService.getAllActiveStudents();
			model.addAttribute("list", list);
			return "STU003";
		}
	    
	    @GetMapping("/setupupdatestudent")
		public ModelAndView setupUpdateStudent(@RequestParam("id") String studentId,ModelMap model) {
	    	StudentBean studentBean = stuService.getStudentsByStudentId(studentId)
	                .orElseThrow(() -> new RuntimeException("Student not found for id: " + studentId));
	    	List<CourseBean> list = courseService.getAllActiveCourses();
			model.addAttribute("list", list);	    	
	        return new ModelAndView("STU002", "stuBean", studentBean);
			
		}
				
		
		@PostMapping("/updatestudent")
		public String updateuser(@RequestParam("inputPhoto") MultipartFile inputPhoto,@ModelAttribute("stuBean")@Validated StudentBean stuBean, BindingResult bs, ModelMap model) throws IllegalStateException, IOException{
			
			String oldPhotoName = stuBean.getPhoto();
					String imageFileName=null;
					if (inputPhoto.getOriginalFilename() != null && !inputPhoto.getOriginalFilename().isEmpty()) {
						imageFileName = inputPhoto.getOriginalFilename();
						String uploadpath = "D:\\springBoot_Wordspace\\StudentManagement_springdata_jpa\\src\\main\\resources\\static\\resources\\img\\";
						File destination = new File(uploadpath + imageFileName);
						try {
				        	inputPhoto.transferTo(destination);
				        } catch (IllegalStateException e) {
				            e.printStackTrace();
				        } catch (IOException e) {
				            e.printStackTrace();
				        }
					}else {
						imageFileName = oldPhotoName;
					}
					stuBean.setPhoto(imageFileName);
			
			if(bs.hasErrors()) {
			return "STU002";
		}
			// Set the selected courses in the student bean
		     List<CourseBean> selectedCourses = new ArrayList<>();
		     if (stuBean.getAttendlist() != null) {
		         for (String courseId : stuBean.getAttendlist()) {
		             // Fetch the existing CourseBean from the database
		             CourseBean existingCourse = courseService.getCourseById(courseId);

		             if (existingCourse != null) {
		                 // Preserve the existing course name and action
		                 selectedCourses.add(existingCourse);
		             }
		         }
		     }
		     stuBean.setAttend(selectedCourses);
		     
			stuService.update(stuBean, stuBean.getStudentId());
			return "redirect:/showstudent";
		}
		
		@GetMapping(value="/deletestudent")
		public String deleteStudent(@RequestParam ("id") String studentId,ModelMap model) {
			
			int res = stuService.updateStudentAction(studentId, "Inactive");
			if(res>0) {
				return "redirect:/showstudent";
			}
			model.addAttribute("error","Delete Fail");
	        return "redirect:/showstudent";
		        		
		}
		
		
		@GetMapping(value = "/searchstudent")
		public String searchStudent(@RequestParam(name = "search", required = false) String search,
		                            @RequestParam(name = "action", defaultValue = "Active") String action,
		                            ModelMap model) {

		    List<StudentBean> searchResult;

		    if (search != null && !search.isEmpty()) {
		        // If a search term is provided, search with the term
		        searchResult = stuService.getBySearch(search);
		    } else {
		        // If no search term is provided, show only active students
		        searchResult = stuService.getByAction(action);
		    }

		    if (searchResult.isEmpty()) {
		        model.addAttribute("msg", "No results found.");
		    }

		    model.addAttribute("list", searchResult);

		    return "searchstudent";
		}
}
