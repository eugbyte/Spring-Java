package sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sms.model.Course;
import sms.model.CourseEnrollment;
import sms.model.Student;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository <CourseEnrollment, Integer> {

	public List<CourseEnrollment> findByStudent(Student student);
}
