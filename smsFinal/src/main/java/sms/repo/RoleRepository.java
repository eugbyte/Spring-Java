package sms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sms.model.Role;

public interface RoleRepository extends JpaRepository <Role, Integer>{

}
