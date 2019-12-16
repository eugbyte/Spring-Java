package sms.viewbag;

public class ViewBagCgpa {

	private String studentName;
	private double grade;
	public ViewBagCgpa() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ViewBagCgpa(String studentName, double grade) {
		super();
		this.studentName = studentName;
		this.grade = grade;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	
}
