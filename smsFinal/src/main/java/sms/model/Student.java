package sms.model;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Student {
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int studentId;
	private String studentName;
	private String gender;
	private String degree;
	private int semester;
	private String mobile;
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Student(String studentName, String gender, String degree, int semester, String mobile) {
		super();
		this.studentName = studentName;
		this.gender = gender;
		this.degree = degree;
		this.semester = semester;
		this.mobile = mobile;
	}
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", gender=" + gender + ", degree="
				+ degree + ", semester=" + semester + ", mobile=" + mobile + "]";
	}
	
	
	

}
