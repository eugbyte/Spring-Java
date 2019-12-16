package sms.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Course {
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int courseCode;
	private String courseName;
	private int courseUnit;
	
	@ManyToOne
	@JoinColumn(name="departmentId")
	private Department department;
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Course(String courseName, int courseUnit, Department department) {
		super();
		this.courseName = courseName;
		this.courseUnit = courseUnit;
		this.department = department;
	}
	
	
	public int getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(int courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getCourseUnit() {
		return courseUnit;
	}
	public void setCourseUnit(int courseUnit) {
		this.courseUnit = courseUnit;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
		
	@Override
	public String toString() {
		return "Course [courseCode=" + courseCode + ", courseName=" + courseName + ", courseUnit=" + courseUnit
				+ ", department=" + department + "]";
	}
	
	
	

}
