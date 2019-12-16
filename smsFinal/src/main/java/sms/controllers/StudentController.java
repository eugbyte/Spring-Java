package sms.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sms.model.Course;
import sms.model.CourseEnrollment;
import sms.model.Student;
import sms.model.StudentGrade;
import sms.model.User;
import sms.repo.CourseEnrollmentRepository;
import sms.repo.CourseRepository;
import sms.services.StudentService;
import sms.services.StudentServiceImpl;
import sms.services.UserDetailService;

@Controller
@RequestMapping("/studentpage")
public class StudentController {
	
	
	@Autowired
	private StudentService studentService ;
	
	@Autowired
	private UserDetailService uService;
	
	@Autowired
	private CourseRepository cRepo;
	
	@Autowired
	private CourseEnrollmentRepository ceRepo;
	
	@GetMapping("/grades")
	public String grades(Model model, Principal principal) {
	String username = principal.getName();
	System.out.println("username is: " + username);
	User user = uService.retrieveUserByName(username);
	int studentId = user.getStudent().getStudentId();
	System.out.println("userId is: " + studentId);
	Student student = studentService.findStudentById(studentId);

	
	ArrayList<StudentGrade> sg = studentService.getStudentGrades(student);

	model.addAttribute("studentgrade", sg);
	model.addAttribute("student", student);
	
	
	double totalScore=0;
	double totalUnits=0;
	for(StudentGrade s:sg) {
		if(s.getGrade()!=null) {
			totalScore+=s.getGrade()*s.getCourse().getCourseUnit();
			totalUnits+=s.getCourse().getCourseUnit();
		}
		
	}
	
	
	/*
	double totalScore = sg.stream()
			.filter(g -> g.getGrade() != null)
			.mapToDouble(g -> g.getGrade())
			.sum();
	double totalUnits = sg.stream()
			.filter(g -> g.getGrade() != null)
			.mapToDouble(g -> g.getCourse().getCourseUnit())
			.sum();
			*/
	double gpa = totalScore / totalUnits;
	model.addAttribute("gpa", gpa);
	
	return "Student/StudentOnly/grades";

	}

	@GetMapping("/availableCourses")
	public String availableCourses(Model model, Principal principal) {
		String username = principal.getName();
		User user = uService.retrieveUserByName(username);
		int studentId = user.getStudent().getStudentId();
		
		List<Course> allCourses = cRepo.findAll();
		
		List<Course> currentCourses = studentService.retrieveCurrentCourses (studentId);
		List<Course> coursesAlreadyReviewed = studentService.coursesAlreadyReviewed(studentId);
		
		List<Course> availableCourses = allCourses.stream()
				.filter(c -> !currentCourses.contains(c))
				.filter(c -> !coursesAlreadyReviewed.contains(c))
				.collect(Collectors.toList());
		
		//filter courses by courses student is already in
		model.addAttribute("course", availableCourses);
		return "Student/StudentOnly/availableCourses";

	}
	
	@GetMapping("/applicationstatus")
	public ModelAndView getCourseApplicationStatus(Principal principal) {
		String username = principal.getName();
		User user = uService.retrieveUserByName(username);
		Student student = user.getStudent();
		
		List<CourseEnrollment> ces = ceRepo.findByStudent(student);
		ModelAndView mv = new ModelAndView("Student/StudentOnly/applicationStatus");
		mv.addObject("enrollments", ces);
		return mv;
		
		
	}


}
