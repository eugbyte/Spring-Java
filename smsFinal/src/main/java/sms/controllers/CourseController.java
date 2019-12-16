package sms.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
import sms.model.CourseEnrollment;
import sms.model.Department;
import sms.model.Student;
import sms.model.StudentGrade;
import sms.repo.CourseEnrollmentRepository;
import sms.repo.CourseRepository;
import sms.repo.DepartmentRepository;
import sms.repo.StudentGradeRepository;
import sms.services.CourseService;

@Controller
@RequestMapping("/adminpage/courses")
public class CourseController {
	@Autowired
	private CourseRepository crepo;
	@Autowired
	private DepartmentRepository dRepo;
	@Autowired
	private CourseService cService;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// binder.addValidators(new ProductValidator());
	}

	/*
	 * @GetMapping("/list") public String listAll(Model model) { ArrayList<Course>
	 * clist = new ArrayList<Course>(); clist.addAll(crepo.findAll());
	 * model.addAttribute("courses", clist); return "/Course/course"; }
	 */
	@GetMapping("/page")
	public ModelAndView listAllPages(@RequestParam("page") Optional<Integer> page, 
		      @RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(0);
		int currentSize = size.orElse(4);
		Page<Course> pageResult = crepo.findAll( PageRequest.of( currentPage, currentSize ) );
		
		int totalPages = pageResult.getTotalPages();
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		IntStream.rangeClosed(0, totalPages - 1).forEach(pageNumbers::add);
		
		ModelAndView mv = new ModelAndView("/Course/course");
			
		mv.addObject("pageResult", pageResult);
		mv.addObject("pageNumbers", pageNumbers);
		
		return mv;
	}
	@GetMapping("/add")
	// public String showAddForm(@ModelAttribute Product product
	public String showAddForm(Model model) {
		Course course = new Course();
		model.addAttribute("new_course", course);
		
		List<Department> departments = dRepo.findAll();
		model.addAttribute("departments_", departments);

		return "/Course/courseform";

	}

	@PostMapping("/save")
	@RequestMapping(value = "/save", path = "/save", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html")
	public String saveCourse(@Valid @ModelAttribute("new_course") Course course, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("new_course",course);
			return "/Course/courseform";
		}
		crepo.save(course);
		return "redirect:/adminpage/courses/page";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(Model model, @PathVariable("id") Integer id) {
		Course course = crepo.findById(id).get();
		model.addAttribute("new_course", course);
		List<Department> departments = dRepo.findAll();
		model.addAttribute("departments_", departments);
		return "/Course/courseform";

	}

	@GetMapping("/delete/{id}")
	public String deleteMethod(Model model, @PathVariable("id") Integer id) {
		Course course = crepo.findById(id).get();
		try {
			crepo.delete(course);
		} catch (Exception exception) {
			String errorMessage = "There are grades that depend on the course you are trying to delete.";
			errorMessage += "\nPlease delete those grades first";
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/adminpage/courses/page";
		}
		
		return "redirect:/adminpage/courses/page";

	}
	
	@RequestMapping("/retrieveEnrollment")
	public ModelAndView retrieveEnrollment(@RequestParam int selectedCourseId) {
		List<Student> students = cService.retrieveStudentByCourseId(selectedCourseId);
		ModelAndView mv = new ModelAndView("/Course/StudentEnrollment/studentlist");
		mv.addObject("students", students);
		mv.addObject("courseCode", selectedCourseId);
		return mv;
	}
	
	
}
