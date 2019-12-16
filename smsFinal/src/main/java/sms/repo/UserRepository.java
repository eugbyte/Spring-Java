package sms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sms.model.User;

public interface UserRepository extends JpaRepository <User, Integer>{

	public User findByUsername(String userName);
}
