package sms.model;

import javax.persistence.*;

@Entity
public class StudentGrade {
	@Id 
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private Double grade;
	private int semester;
	@ManyToOne
	@JoinColumn(name="courseCode")
	private Course course;
	@ManyToOne
	@JoinColumn(name="studentId")
	private Student student;
	
	public StudentGrade() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentGrade(Double grade, int semester, Course course, Student student) {
		super();
		this.grade = grade;
		this.semester = semester;
		this.course = course;
		this.student = student;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "StudentGrade [id=" + id + ", grade=" + grade + ", semester=" + semester + ", course=" + course
				+ ", student=" + student + "]";
	}

	
	
	
	
	

}
