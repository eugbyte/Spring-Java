package sms.services;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import sms.model.Course;
import sms.model.Faculty;
import sms.model.Student;
import sms.model.StudentGrade;
import sms.viewbag.ViewBagStudentGradeAndStudent;

public interface FacultyService {

	public Map <Student, StudentGrade> retrieveStudentGradeMap (Course course);
	
	public Faculty retrieveFaculty (String name);
	
	public List<ViewBagStudentGradeAndStudent> retrieveStudentGradeViewBag (Course course);
	
	public List<Course> getAllCourses (Principal principal);
}
