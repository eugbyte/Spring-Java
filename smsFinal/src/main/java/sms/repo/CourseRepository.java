package sms.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sms.model.Course;
import sms.model.Department;


public interface CourseRepository extends JpaRepository <Course, Integer> {

	public List<Course> findByDepartment (Department department);
	//public List<Course> findByDepartment_Faculty_FacultyId(int facultyId);
	
	public ArrayList<Course> findAll();
	public Page<Course> findAll(Pageable pageable);

	
}
