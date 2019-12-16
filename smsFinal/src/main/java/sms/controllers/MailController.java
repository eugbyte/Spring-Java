package sms.controllers;

import java.security.Principal;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sms.mail.MailSender;
import sms.model.Course;
import sms.model.CourseEnrollment;
import sms.model.Student;
import sms.model.User;
import sms.repo.CourseEnrollmentRepository;
import sms.repo.CourseRepository;
import sms.services.CourseService;
import sms.services.StudentService;
import sms.services.UserDetailService;



@Controller
public class MailController {
	
	@Autowired
	private CourseService cService;	
	@Autowired
	private CourseEnrollmentRepository ceRepo;	
	@Autowired
	private UserDetailService uService;
	@Autowired
	private StudentService sService;
	
	private MailSender mailSender;

	public MailController(MailSender smtp) {
		this.mailSender = smtp;
	}

	@RequestMapping("/studentpage/mail")
	public String mail(@RequestParam String courseCode, Principal principal) throws MessagingException {
		
		int courseId = Integer.parseInt(courseCode);
		Course course = cService.findCourseById(courseId);
		
		String username = principal.getName();
		User user = uService.retrieveUserByName(username);
		int studentId = user.getStudent().getStudentId();
		Student student = sService.findStudentById(studentId);
		
		CourseEnrollment cr = new CourseEnrollment();
		cr.setCourse(course);
		cr.setStudent(student);
		cr.setStatus("PENDING");
		ceRepo.save(cr);
		
		mailSender.send("lara1042242532@gmail.com", "Enrolment Request submitted", "Please check system to approve/reject enrollment request.");
		
		return "Student/StudentOnly/MailConfirmation";
	}
}
