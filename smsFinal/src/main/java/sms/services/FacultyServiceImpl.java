package sms.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sms.model.Course;
import sms.model.Department;
import sms.model.Faculty;
import sms.model.Student;
import sms.model.StudentGrade;
import sms.model.User;
import sms.repo.CourseRepository;
import sms.repo.FacultyRepository;
import sms.repo.StudentGradeRepository;
import sms.viewbag.ViewBagStudentGradeAndStudent;

@Service
public class FacultyServiceImpl implements FacultyService {

	@Autowired
	private FacultyRepository fRepo;
	@Autowired
	private CourseRepository cRepo;
	@Autowired
	private StudentGradeRepository sgRepo;	
	@Autowired
	private UserDetailService userService;
	
	public List<Course> getAllCourses (Principal principal) {
		String username = principal.getName();
		User user =  userService.retrieveUserByName(username);
		System.out.println("user is " + user);
		int facultyId = user.getFaculty().getFacultyId();
		
		Faculty faculty = fRepo.findById(facultyId).get();
		System.out.println(faculty);
		
		Department department = faculty.getDepartment();
		List<Course> courses = cRepo.findByDepartment(department);
		return courses;
	}
	
	public List<ViewBagStudentGradeAndStudent> retrieveStudentGradeViewBag (Course course) {
		List<StudentGrade> studentGrades = sgRepo.findByCourse(course);
		List<Student> students = studentGrades.stream()
				.map(grades -> grades.getStudent())
				.collect(Collectors.toList());
		
		//Convert to array so that can loop through both iterables simultaneously 
		StudentGrade[] studentGradesArr = studentGrades.stream().toArray(StudentGrade[]::new);
		Student[] studentArr = students.stream().toArray(Student[]::new);
		
		List<ViewBagStudentGradeAndStudent> viewBagGrades = new ArrayList<ViewBagStudentGradeAndStudent> ();
		
		for (int i = 0; i < studentGradesArr.length; i++) {
			ViewBagStudentGradeAndStudent vbg = new ViewBagStudentGradeAndStudent(studentArr[i], studentGradesArr[i]);
			viewBagGrades.add(vbg);
		}
		return viewBagGrades;
	}
	
	public Faculty retrieveFaculty (String name) {
		return fRepo.findByFacultyName(name);
	}	
	
	public Map <Student, StudentGrade> retrieveStudentGradeMap (Course course) {
		List<StudentGrade> studentGrades = sgRepo.findByCourse(course);
		Map <Student, StudentGrade> map = new HashMap<Student, StudentGrade>();
		studentGrades.forEach(grade -> map.put(grade.getStudent(), grade));
		return map;
	}
	
}
