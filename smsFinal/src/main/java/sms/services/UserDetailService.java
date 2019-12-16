package sms.services;

import java.security.Principal;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sms.model.User;
import sms.repo.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {
	//UserDetailsService is a core interface which loads user-specific data. 
	//UserDetails provides core user information. 
	
	@Autowired
	private UserRepository uRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = uRepository.findByUsername(username);
		if (user == null) {
			user = new User();
		}
		return new UserDetail(user);
	}
	
	public User retrieveUserByName (String userName) {
		return uRepository.findByUsername(userName);
	}
	
	

}
