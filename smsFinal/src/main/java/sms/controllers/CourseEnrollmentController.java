package sms.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sms.model.Course;
import sms.model.CourseEnrollment;
import sms.model.Student;
import sms.model.StudentGrade;
import sms.repo.CourseEnrollmentRepository;
import sms.repo.StudentGradeRepository;

@Controller
@RequestMapping("/adminpage/enrollment")
public class CourseEnrollmentController {

	@Autowired
	private CourseEnrollmentRepository ceRepo;
	@Autowired
	private StudentGradeRepository sgRepo;
	
	@GetMapping("/review")
	public ModelAndView reviewRequests() {
		List<CourseEnrollment> ces = ceRepo.findAll();
		ces = ces.stream()
				.filter(ce -> ce.getStatus().equals("PENDING") )
				.collect(Collectors.toList());
		
		System.out.println("printing " + ces);
		ces.forEach(System.out::println);
		
		ModelAndView mv = new ModelAndView("/CourseEnrollment/reviewRequests");
		mv.addObject("enrollments", ces);
		return mv;
	}
	
	@GetMapping("/process/{id}")
	public String decideRequest(@PathVariable("id") Integer id, @RequestParam String status) {
		int ceId = id;
		CourseEnrollment ce = ceRepo.findById(ceId).get();
		ce.setStatus(status);
		ceRepo.save(ce);
		
		if (status.equals("REJECT")) {
			return "redirect:/adminpage/enrollment/review";
		}
		
		Student student = ce.getStudent();
		System.out.println(student);
		Course course = ce.getCourse();
		System.out.println(course);
		
		StudentGrade sg = new StudentGrade();
		sg.setCourse(course);
		sg.setGrade(null);
		sg.setSemester(student.getSemester());
		sg.setStudent(student);
		sg.setId(sgRepo.findAll().size() + 1);
		sgRepo.save(sg);
			
		return "redirect:/adminpage/enrollment/review";
		
	}
}
