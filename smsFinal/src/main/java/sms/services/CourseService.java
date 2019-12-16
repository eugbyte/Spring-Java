package sms.services;

import java.util.ArrayList;
import java.util.List;

import sms.model.Course;
import sms.model.Student;

public interface CourseService {

	public Course findCourseById(int courseId);
	public List<Student> retrieveStudentByCourseId(int courseId);
		
}
