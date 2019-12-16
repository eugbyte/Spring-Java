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

import sms.model.Course;
import sms.model.Department;
import sms.model.Faculty;
import sms.repo.DepartmentRepository;
import sms.repo.FacultyRepository;

@Controller
@RequestMapping("/adminpage/faculty")
public class FacultyController {

	@Autowired
	private FacultyRepository frepo;
	
	@Autowired
	private DepartmentRepository dRepo;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// binder.addValidators(new ProductValidator());
	}

	/*
	 * @GetMapping("/list") public String listAll(Model model) { ArrayList<Faculty>
	 * flist = new ArrayList<Faculty>(); flist.addAll(frepo.findAll());
	 * model.addAttribute("faculty", flist); return "/Faculty/faculty"; }
	 */
	@GetMapping("/page")
	public ModelAndView listAllPages(@RequestParam("page") Optional<Integer> page, 
		      @RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(0);
		int currentSize = size.orElse(4);
		Page<Faculty> pageResult = frepo.findAll( PageRequest.of( currentPage, currentSize ) );
		
		int totalPages = pageResult.getTotalPages();
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		IntStream.rangeClosed(0, totalPages - 1).forEach(pageNumbers::add);
		
		ModelAndView mv = new ModelAndView("/Faculty/faculty");
			
		mv.addObject("pageResult", pageResult);
		mv.addObject("pageNumbers", pageNumbers);
		
		return mv;
	}
	

	@PostMapping("/save")
	@RequestMapping(value = "/save", path = "/save", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html")
	public String saveFaculty(@Valid @ModelAttribute("faculty") Faculty faculty, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "/Faculty/facultyform";
		}
		frepo.save(faculty);
		return "redirect:/adminpage/faculty/page";
	}
	
	@GetMapping("/add")
	// public String showAddForm(@ModelAttribute Product product
	public String showAddForm(Model model) {
		Faculty faculty = new Faculty();
		model.addAttribute("new_faculty", faculty);
		
		List<Department> departments = dRepo.findAll();
		model.addAttribute("departments_", departments);
		return "/Faculty/facultyform";
	}

	@GetMapping("/edit/{facultyId}")
	public String showEditForm(Model model, @PathVariable("facultyId") Integer facultyId) {  
		Faculty faculty = frepo.findById(facultyId).get();
		model.addAttribute("new_faculty", faculty);
		List<Department> departments = dRepo.findAll();
		model.addAttribute("departments_", departments);
		return "/Faculty/facultyform";

	}

	@GetMapping("/delete/{facultyId}")
	public String deleteMethod(Model model, @PathVariable("facultyId") Integer facultyId) {
		Faculty faculty = frepo.findById(facultyId).get();
		try {
			frepo.delete(faculty);
			model.addAttribute("faculty", faculty);
			return "redirect:/adminpage/faculty/page";
		} catch (Exception error) {
			String errorMessage = "User account is depend on the course you are trying to delete.";
			errorMessage += " Please delete that acount first.";
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/adminpage/faculty/page";
		}
		
		

	}

}
