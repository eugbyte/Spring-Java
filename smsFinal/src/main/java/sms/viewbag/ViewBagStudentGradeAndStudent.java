package sms.viewbag;

import sms.model.Student;
import sms.model.StudentGrade;

public class ViewBagStudentGradeAndStudent {

	private Student student;
	private StudentGrade grade;
	
	public ViewBagStudentGradeAndStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ViewBagStudentGradeAndStudent(Student student, StudentGrade grade) {
		super();
		this.student = student;
		this.grade = grade;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public StudentGrade getGrade() {
		return grade;
	}

	public void setGrade(StudentGrade grade) {
		this.grade = grade;
	}
	
	
}
