package sms.controllers;

import java.util.ArrayList

;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sms.model.Student;
import sms.repo.StudentRepository;

@Controller
@RequestMapping("/adminpage/student")
public class StudentCRUDController {

	@Autowired
	private StudentRepository sRepo;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// binder.addValidators(new ProductValidator());
	}

	/*
	@GetMapping("/list")
	public String listAll(Model model) {
		ArrayList<Student> slist = new ArrayList<Student>();
		slist.addAll(sRepo.findAll());
		model.addAttribute("students", slist);
		return "/Student/students";
	}
	*/
	
	@GetMapping("/page")
	public ModelAndView listAllPages(@RequestParam("page") Optional<Integer> page, 
		      @RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(0);
		int currentSize = size.orElse(4);
		Page<Student> pageResult = sRepo.findAll( PageRequest.of( currentPage, currentSize ) );
		
		int totalPages = pageResult.getTotalPages();
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		IntStream.rangeClosed(0, totalPages - 1).forEach(pageNumbers::add);
		
		ModelAndView mv = new ModelAndView("/Student/studentPaged");
			
		mv.addObject("pageResult", pageResult);
		mv.addObject("pageNumbers", pageNumbers);
		
		return mv;
	}

	@GetMapping("/add")
	public String showAddForm(Model model) {
		Student student = new Student();
		model.addAttribute("new_student", student);

		return "/Student/studentform";

	}

	@PostMapping("/save")
	@RequestMapping(value = "/save", path = "/save", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html")
	public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("new_student",student);
			return "studentform";
		}
		sRepo.save(student);
		return "redirect:/adminpage/student/page";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(Model model, @PathVariable("id") Integer id) {
		Student student = sRepo.findById(id).get();
		model.addAttribute("new_student", student);
		return "/Student/studentform";
		
		

	}

	@GetMapping("/delete/{id}")
	public String deleteMethod(Model model, @PathVariable("id") Integer id) {
		Student student = sRepo.findById(id).get();
		try {
			sRepo.delete(student);
			model.addAttribute("student", student);
		} catch (Exception exception) {
			System.out.println(exception);
			
			String errorMessage = "There are user accounts/grades that depend on the student you are trying to delete.";
			errorMessage += "\nPlease delete those user accounts first";
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/adminpage/student/page";
		}
		
		
		return "redirect:/adminpage/student/page";
	}
}
