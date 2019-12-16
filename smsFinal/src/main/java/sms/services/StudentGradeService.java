package sms.services;

import java.security.Principal;
import java.util.List;

import sms.model.StudentGrade;

public interface StudentGradeService {

	public StudentGrade findStudentGradeById(int id);
	public StudentGrade updateStudentGrade(StudentGrade sg);
	public List cGpaSummary ();
	public List <StudentGrade> retrieveAllGrades ();
	List <StudentGrade> filterGradesByRangeAndCourse (int min, int max, List<Integer> courseIds, Principal principal);
}
