package sms.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
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

import sms.model.Department;
import sms.model.Student;
import sms.repo.DepartmentRepository;

@Controller
@RequestMapping("/adminpage/department")
public class DepartmentController {

	@Autowired
	private DepartmentRepository drepo;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// binder.addValidators(new ProductValidator());
	}

	/*
	 * @GetMapping("/list") public String listAll(Model model) {
	 * ArrayList<Department> dlist = new ArrayList<Department>();
	 * dlist.addAll(drepo.findAll()); model.addAttribute("department", dlist);
	 * return "/Department/department"; }
	 */
	@GetMapping("/page")
	public ModelAndView listAllPages(@RequestParam("page") Optional<Integer> page, 
		      @RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(0);
		int currentSize = size.orElse(4);
		Page<Department> pageResult = drepo.findAll( PageRequest.of( currentPage, currentSize ) );
		
		int totalPages = pageResult.getTotalPages();
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		IntStream.rangeClosed(0, totalPages - 1).forEach(pageNumbers::add);
		
		ModelAndView mv = new ModelAndView("/Department/department");
			
		mv.addObject("pageResult", pageResult);
		mv.addObject("pageNumbers", pageNumbers);
		
		return mv;
	}
	@GetMapping("/add")
	// public String showAddForm(@ModelAttribute Product product
	public String showAddForm(Model model) {
		Department department = new Department();
		model.addAttribute("new_department", department);

		return "/Department/departmentform";

	}

	@PostMapping("/save")
	@RequestMapping(value = "/save", path = "/save", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/html")
	public String saveDepartment(@Valid @ModelAttribute("department") Department department,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "departmentform";
		}
		drepo.save(department);
		return "redirect:/adminpage/department/page";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(Model model, @PathVariable("id") Integer id) {
		// Product product=new Product();
		Department department = drepo.findById(id).get();
		model.addAttribute("new_department", department);
		return "/Department/departmentform";

	}

	@GetMapping("/delete/{id}")
	public String deleteMethod(Model model, @PathVariable("id") Integer id) {
		Department department = drepo.findById(id).get();
		
		try {
			drepo.delete(department);
		} catch (Exception exception) {
			String errorMessage = "There are other courses or faculty that depend on the department you seek to delete.";
			errorMessage += "\nDelete those first.";
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/adminpage/department/page";
		} 		
		model.addAttribute("errorMessage", "this error message will not be shown");
		return "redirect:/adminpage/department/page";

	}

}
