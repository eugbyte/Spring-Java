package sms.repo;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sms.model.Department;

public interface DepartmentRepository extends JpaRepository <Department, Integer> {

	public ArrayList<Department> findAll();
	public Page<Department> findAll(Pageable pageable);
}
