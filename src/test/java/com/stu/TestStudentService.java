package com.stu;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.stu.dao.StudentRespository;
import com.stu.dao.StudentService;
import com.stu.model.StudentBean;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class TestStudentService {
	@Autowired
	private StudentService studentService;
	@MockBean
	private StudentRespository stdRepo;
	
	 @Test
	 public   void testRegisterStudent() {
	        StudentBean studentBean = new StudentBean();
	        studentBean.setStudentId("STD001");
	        studentBean.setStudentName("Eh Eh");
	        studentBean.setStudentPhone("09123456789");
	        studentBean.setEdu("PHD");
	        studentBean.setDob("12-12-2002");
	        studentBean.setGender("female");
	        studentBean.setPhoto("photo");
	        when(stdRepo.save(studentBean)).thenReturn(studentBean);
	        boolean result = studentService.registerStudent(studentBean);
	        assertEquals(true,result);
	      
	    }
	 @Test
	 public void testgetAllActiveStudentTest() {
		 List<StudentBean> activeStud=new ArrayList<StudentBean>();
		 StudentBean searchstud=new StudentBean();
		 searchstud.setStudentId("STD001");
		 searchstud.setStudentName("Eh Eh");
		 searchstud.setStudentPhone("09123456789");
		 searchstud.setEdu("PHD");
		 searchstud.setDob("12-12-2002");
		 searchstud.setGender("female");
		 searchstud.setPhoto("photo");
		 searchstud.setAction("Active");
		 StudentBean searchstuds=new StudentBean();
		 searchstuds.setStudentId("STD001");
		 searchstuds.setStudentName("Eh Eh");
		 searchstuds.setStudentPhone("09123456789");
		 searchstuds.setEdu("PHD");
		 searchstuds.setDob("12-12-2002");
		 searchstuds.setGender("female");
		 searchstuds.setPhoto("photo");
		 searchstuds.setAction("Active");
		 activeStud.add(searchstud);
		 activeStud.add(searchstuds);
		 when(stdRepo.findAllActiveStudents("Active")).thenReturn(activeStud);
		 List<StudentBean> result=studentService.getAllActiveStudents();
		 assertEquals(activeStud.size(), result.size());
		 for(int i=0; i<activeStud.size();i++) {
			 assertEquals(activeStud.get(i), result.get(i));
		 }
		 verify(stdRepo,times(1)).findAllActiveStudents("Active");
			 
		 
	 }
	 @Test
	 public void testgetStudentByStudentIdTest() {
		 String stdId="STD001";
		 StudentBean std=new StudentBean();
		 std.setStudentId(stdId);
		 std.setStudentName("Eh Eh");
		 std.setStudentPhone("09123456789");
		 std.setEdu("PHD");
		 std.setDob("12-12-2002");
		 std.setGender("female");
		 std.setPhoto("photo");
		 when(stdRepo.findById(stdId)).thenReturn(Optional.of(std));
		 Optional<StudentBean> result=studentService.getStudentsByStudentId(stdId);
		 AssertTrue(result.isPresent());
		 assertEquals(stdId, result.get().getStudentId());
		 assertEquals("Eh Eh", result.get().getStudentName());
		 assertEquals("09123456789", result.get().getStudentPhone());
		 assertEquals("PHD", result.get().getEdu());
		 assertEquals("12-12-2002", result.get().getDob());
		 assertEquals("female", result.get().getGender());
		 assertEquals("photo", result.get().getPhoto());
		 verify(stdRepo,times(1)).findById(stdId);
	 }
	private void AssertTrue(boolean present) {
		
		
	}
	@Test
	public void saveTest() {
		StudentBean setStd=new StudentBean();
		setStd.setStudentId("STD001");
		setStd.setStudentName("Eh Eh");
		setStd.setStudentPhone("09123456789");
		setStd.setEdu("PHD");
		setStd.setDob("12-12-2002");
		setStd.setGender("female");
		setStd.setPhoto("photo");
		studentService.save(setStd);
		verify(stdRepo,times(1)).save(setStd);
	}
	@Test
	public void deleteTest() {
		studentService.delete("STD001");
		verify(stdRepo,times(1)).deleteById("STD001");
	}
	@Test
	public void testUpdate() {
		StudentBean std=new StudentBean();
		std.setStudentId("STD001");
		std.setStudentPhone("09123456789");
		std.setEdu("PHD");
		std.setDob("12-12-2002");
		std.setGender("female");
		std.setPhoto("photo");
		studentService.update(std, "STD001");
		verify(stdRepo,times(1)).save(std);
	}
	@Test
	public void testGetBySearch() {
		String search = "Eh Eh";
		List<StudentBean> std=new ArrayList<StudentBean>();
		StudentBean searchstd=new StudentBean();
		searchstd.setStudentId("STD001");
		searchstd.setStudentPhone("09123456789");
		searchstd.setEdu("PHD");
		searchstd.setDob("12-12-2002");
		searchstd.setGender("female");
		searchstd.setPhoto("photo");
		StudentBean searchstd1=new StudentBean();
		searchstd1.setStudentId("STD001");
		searchstd1.setStudentPhone("09123456789");
		searchstd1.setEdu("PHD");
		searchstd1.setDob("12-12-2002");
		searchstd1.setGender("female");
		searchstd1.setPhoto("photo");
		std.add(searchstd);
		std.add(searchstd1);
		when(stdRepo.findBySearch(search)).thenReturn(std);
		List<StudentBean> result=studentService.getBySearch(search);
		assertThat(result).hasSize(2); 
	}
	@Test
	public void testGetByAction() {
		String action="Active";
		List<StudentBean> std=new ArrayList<StudentBean>();
		StudentBean searchstd=new StudentBean();
		searchstd.setStudentId("STD001");
		searchstd.setStudentPhone("09123456789");
		searchstd.setEdu("PHD");
		searchstd.setDob("12-12-2002");
		searchstd.setGender("female");
		searchstd.setPhoto("photo");
		StudentBean searchstd1=new StudentBean();
		searchstd1.setStudentId("STD001");
		searchstd1.setStudentPhone("09123456789");
		searchstd1.setEdu("PHD");
		searchstd1.setDob("12-12-2002");
		searchstd1.setGender("female");
		searchstd1.setPhoto("photo");
		std.add(searchstd);
		std.add(searchstd1);
		when(stdRepo.findByAction(action)).thenReturn(std);
		List<StudentBean> result=studentService.getByAction(action);
		assertThat(result).hasSize(2);
	}
	@Test
	public void testUpdateStudByAction() {
		String stdId="STD001";
		String action="Active";
		when(stdRepo.updateStudentAction(stdId, action)).thenReturn(1);
		int result=studentService.updateStudentAction(stdId, action);
		verify(stdRepo,times(1)).updateStudentAction(stdId, action);
		assertThat(result).isEqualTo(1);
	}
	@Test
	public void generateStudIdTest() {
		
		when(stdRepo.getNextStudentId()).thenReturn(1L);
		String result=studentService.generateStudentId();
		assertEquals("STU001", result);
		verify(stdRepo,times(1)).getNextStudentId();
	}
	
}
