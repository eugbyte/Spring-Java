package sms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	// It is used throughout the framework as a user DAO and is the strategy used by the DaoAuthenticationProvider. The interface requires only one read-only method, which simplifies support for new data-access strategies.
	@Autowired
	private UserDetailsService userDetailsService;
	
	//Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		//inMemoryAuthentication create a user in memory, where you have to further specify the username, password and role
		auth.inMemoryAuthentication();
		
	}
	
	@Autowired	
	private SmsAuthenticationSuccessHandler authenticationSuccessHandler;
	
	//Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//HttpSecurity allows you to configure what are the paths and restrictions
		http.authorizeRequests()
			.antMatchers("/adminpage/**").hasRole("ADMIN")	//	/** is a wildcard, e.g. /home, /login etc
			.antMatchers("/studentpage/**").hasRole("STUDENT")
			.antMatchers("/facultypage/**").hasRole("FACULTY")
			.antMatchers("/", "static").permitAll()	
			.and().formLogin()
				.loginPage("/login")
				.successHandler(authenticationSuccessHandler)
			.and().logout()	
				.permitAll();
				
	}
	
	//Spring security gives you a default logout --> localhost:8080/logout
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
		//reason for disabling the password encoder is that the user login details were created manually in MySQL
		//return new BCryptPasswordEncoder();
	}
}
