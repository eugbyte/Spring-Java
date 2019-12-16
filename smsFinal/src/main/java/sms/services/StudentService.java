package sms.services;

import java.util.ArrayList;
import java.util.List;

import sms.model.Course;
import sms.model.Student;
import sms.model.StudentGrade;

public interface StudentService {

	public List<Student> listStudent();
	public Student getStudentName(int id);
	public ArrayList<StudentGrade>  getStudentGrades(Student student);
	public ArrayList<Course> listcourse();
	public Student findStudentById(int id);
	public List<Course> retrieveCurrentCourses (int studentId);
	public List<Course> coursesAlreadyReviewed (int studentId);
}
