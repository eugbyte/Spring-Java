package sms.services;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sms.model.Course;
import sms.model.Student;
import sms.model.StudentGrade;
import sms.repo.StudentGradeRepository;

@Service
public class StudentGradeServiceImpl implements StudentGradeService {

	@Autowired
	private StudentGradeRepository sgRepo;
	
	@Autowired
	private FacultyService fService;
	
	@Autowired
	private StudentGradeService sgService;
	
	public StudentGrade findStudentGradeById(int id) {
		return sgRepo.findById(id).get();
	}
	
	public StudentGrade updateStudentGrade(StudentGrade sg) {
		return sgRepo.save(sg);
	}
	
	public List cGpaSummary () {
		return sgRepo.cGPASummary();
	}
	
	public List <StudentGrade> retrieveAllGrades () {
		return sgRepo.findAll();
	}
	
	public List <StudentGrade> filterGradesByRangeAndCourse (int min, int max, List<Integer> courseIds, Principal principal) {
		List<Course> courses = fService.getAllCourses(principal);
		courses.forEach(c -> System.out.println(c.getCourseCode()));
		System.out.println();
		
		List<Integer> resultCourseCodes = courses.stream()
				.filter(c -> courseIds.contains(c.getCourseCode()))
				.map(c -> c.getCourseCode())
				.collect(Collectors.toList());
		/*
		resultCourseCodes.forEach(System.out::println);
		System.out.println();
		//---fine up to here---
		
		sgService.retrieveAllGrades()
		.stream()
		.filter(g -> resultCourseCodes.contains(g.getCourse().getCourseCode()))
		.forEach(g -> System.out.println(g.getId()));
		System.out.println("grades finished printing");
		System.out.println();
		
		sgService.retrieveAllGrades()
		.stream()
		.filter(g -> resultCourseCodes.contains(g.getCourse().getCourseCode()))
		.filter(g -> (g.getGrade()) >= min)
		.forEach(g -> System.out.println(g.getId()));
		*/
		
		List<StudentGrade> resultGrades = sgService.retrieveAllGrades()
				.stream()
				.filter(g -> g.getGrade() != null )
				.filter(g -> !g.getGrade().isNaN())
				.filter(g -> resultCourseCodes.contains(g.getCourse().getCourseCode()))
				.filter(g -> (g.getGrade()) >= min)
				.filter(g -> (g.getGrade()) <= max)
				.sorted(Comparator.comparingInt( g -> g.getStudent().getStudentId() ) )
				.collect(Collectors.toList());
		
		System.out.println();
		resultGrades.forEach(g -> System.out.println(g.getId()) );
		System.out.println();
		List<Student> resultStudents = resultGrades.stream()
				.map(g -> g.getStudent())
				.collect(Collectors.toList());
		resultStudents.forEach(s -> System.out.print(s.getStudentName()));
		
		return resultGrades;
	}
}
