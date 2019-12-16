package sms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sms.model.Course;
import sms.model.CourseEnrollment;
import sms.model.Student;
import sms.model.StudentGrade;
import sms.repo.CourseEnrollmentRepository;
import sms.repo.CourseRepository;
import sms.repo.StudentGradeRepository;
import sms.repo.StudentRepository;


@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepo;
	@Autowired
	private StudentGradeRepository sgRepo;
	
	@Autowired
	private CourseRepository cRepo;
	
	@Autowired
	private CourseEnrollmentRepository crRepo;
	
	public List<Student> listStudent(){
		return (List<Student>)studentRepo.findAll();
	}

	public Student getStudentName(int id) {
		return studentRepo.findById(id).get();
		
	}
	
	public ArrayList<StudentGrade>  getStudentGrades(Student student) {
		return sgRepo.findByStudent(student);
		
	}
	//@Autowired
	//private StudentGradeRepository gradeRepo;
	
	@Autowired CourseRepository courseRepo;
	public ArrayList<Course> listcourse(){
		return (ArrayList<Course>) courseRepo.findAll();
	}
	
	public Student findStudentById(int id) {
		return studentRepo.findById(id).get();
	}

	public List<Course> retrieveCurrentCourses (int studentId) {
		Student student = findStudentById(studentId);
		List<StudentGrade> sgs = sgRepo.findByStudent(student);
		List<Course> courses = sgs.stream()
				.map(sg -> sg.getCourse())
				.distinct()
				.collect(Collectors.toList());
		courses.forEach(System.out::println);
		return courses;
	}
	
	public List<Course> coursesAlreadyReviewed (int studentId) {
		Student student = findStudentById(studentId);
		List<CourseEnrollment> crs = crRepo.findByStudent(student);
		List<Course> courses = crs.stream()
				.map(cr -> cr.getCourse())
				.collect(Collectors.toList());
		courses.forEach(System.out::println);
		return courses;
	}


}
