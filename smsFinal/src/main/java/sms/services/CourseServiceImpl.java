package sms.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sms.model.Course;
import sms.model.Student;
import sms.model.StudentGrade;
import sms.repo.CourseRepository;
import sms.repo.StudentGradeRepository;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	public CourseRepository cRepository;
	
	@Autowired
	public StudentGradeRepository sgRepo;
	
	public Course findCourseById(int courseId) {
		Course course = cRepository.findById(courseId).get();
		return course;
	}
	
	public List<Student> retrieveStudentByCourseId(int courseId) {
		Course course = cRepository.findById(courseId).get();
		List<StudentGrade> sgs = sgRepo.findByCourse(course);
		List<Student> students = sgs.stream()
				.map(sg -> sg.getStudent())
				.collect(Collectors.toList());
		return students;
		
	}
	
	
	
	
}
