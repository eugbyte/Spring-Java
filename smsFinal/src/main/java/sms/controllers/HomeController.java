package sms.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import sms.model.User;
import sms.services.UserDetailService;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public ModelAndView home (Authentication authentication, HttpSession session) {
		ModelAndView mv = new ModelAndView("/Home/home");
		return mv;
	}

	@Autowired
	private UserDetailService uService;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// binder.addValidators(new customValidator()); 
	} 

	
	@GetMapping("/login")
	public ModelAndView loginPage (@RequestParam(required = false) String error) {		
		User user = new User();
		ModelAndView mv = new ModelAndView("/Home/loginPage");
		mv.addObject("user", user);
		
		if (error != null) {
			mv.addObject("hasError", true);
		}
		return mv;		
	}
	
	@RequestMapping("/logout")
	public String logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/login";
	}
		
	@GetMapping("/adminpage")
	public ModelAndView adminPage (HttpSession session, Authentication authentication) {
		ModelAndView mv = new ModelAndView("/Welcomepage/adminpage");
		GrantedAuthority role = authentication.getAuthorities().stream().findFirst().get();
		System.out.println(role);
		//ROLE_ADMIN
		return mv;
	}
	
	@GetMapping("/studentpage")
	public ModelAndView studentPage () {
		ModelAndView mv = new ModelAndView("/Welcomepage/studentpage");
		return mv;
	}
	
	@GetMapping("/facultypage")
	public ModelAndView facultyPage () {
		ModelAndView mv = new ModelAndView("/Welcomepage/facultypage");
		return mv;
	}

	
}
