package sms.repo;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sms.model.Faculty;

public interface FacultyRepository extends JpaRepository <Faculty, Integer> {

	public Faculty findByFacultyName(String name);
	public ArrayList<Faculty> findAll();
	public Page<Faculty> findAll(Pageable pageable);
}
