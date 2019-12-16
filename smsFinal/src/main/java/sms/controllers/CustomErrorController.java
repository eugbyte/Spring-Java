package sms.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {
     
	@Override
	public String getErrorPath() {
		// Returns the path of the error page.
		return "/error";
	}
	
	@RequestMapping("/error")
	public ModelAndView handlerError (HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/Errorpage/errorPage");
		String errorMessage = "No error";
		
		int responseStatus = response.getStatus();
		
		if(response.getStatus() == HttpStatus.NOT_FOUND.value()) {
			errorMessage = "404 Page not found";
		}
		else if(response.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
			errorMessage = "401 unauthenticated";
		}
		else if(response.getStatus() == HttpStatus.FORBIDDEN.value()) {
			errorMessage = "403 unauthorized";
		}
		else if(response.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			errorMessage = "500 internal server error";
		}
		else {
			errorMessage = "Error";
		}
		
		mv.addObject("errorMessage", errorMessage);
		return mv;
	}
}