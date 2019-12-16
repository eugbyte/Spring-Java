package sms.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sms.model.Student;

public interface StudentRepository extends JpaRepository <Student, Integer> {

	public Page<Student> findAll(Pageable pageable);
}
