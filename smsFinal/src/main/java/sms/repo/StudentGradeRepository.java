package sms.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sms.model.Course;
import sms.model.Student;
import sms.model.StudentGrade;

public interface StudentGradeRepository extends JpaRepository <StudentGrade, Integer> {

	public List<StudentGrade> findByCourse(Course course);
	
	@Query("SELECT sg.student.studentName,  AVG(sg.grade) "
			+ "FROM StudentGrade sg  "
			+ "GROUP BY sg.student")
	public List cGPASummary ();
	
	public List<StudentGrade> findByCourse_CourseCode(Course coursecode);
	
	public ArrayList<StudentGrade> findByStudent(Student student);
	
	
}
