package sms.controllers;

import java.security.Principal
;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import sms.model.Course;
import sms.model.Department;
import sms.model.Faculty;
import sms.model.Student;
import sms.model.StudentGrade;
import sms.model.User;
import sms.repo.CourseRepository;
import sms.services.CourseService;
import sms.services.FacultyService;
import sms.services.StudentGradeService;
import sms.services.UserDetailService;
import sms.validation.StudentGradeValidator;
import sms.viewbag.ViewBagCgpa;
import sms.viewbag.ViewBagStudentGradeAndStudent;

@Controller
@RequestMapping("/facultypage")
public class FacultyCourseAndGradesController {

	@Autowired
	private FacultyService fService;
	
	@Autowired
	private CourseService cService;
	
	@Autowired
	private StudentGradeService sgService;
	
	@Autowired
	private UserDetailService userService;
	
	@GetMapping("/courses")
	public ModelAndView coursePage (Principal principal) {
		List<Course> courses = fService.getAllCourses(principal);
		
		ModelAndView mv = new ModelAndView("/Course/courseoverview");
		mv.addObject("courses", courses);
		return mv;
		
	}
	
	@GetMapping("/grades")
	public ModelAndView gradesPage (@RequestParam int id) {
		Course course = cService.findCourseById(id);
		List<ViewBagStudentGradeAndStudent> viewBagGrades = fService.retrieveStudentGradeViewBag(course);
		
		ModelAndView mv = new ModelAndView("/Grades/gradespage");
		mv.addObject("viewbags", viewBagGrades);
		return mv;		
	}
	
	@RequestMapping(value = "/grades/update/{id}", method = {RequestMethod.GET, RequestMethod.POST} )
	public ModelAndView editGradesForm (@PathVariable int id) {	
		
		StudentGrade sg = sgService.findStudentGradeById(id);
		
		ModelAndView mv = new ModelAndView("/Grades/editgrades");
		mv.addObject("grade", sg);
		//mv.addObject("student", sg.getStudent());
		return mv;		
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new StudentGradeValidator()); 
	}
	
	@PostMapping("/grades/save")
	public String updateGrades (@Valid @ModelAttribute StudentGrade sg, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("hasError", true);
			return "forward:/facultypage/grades/update/" + sg.getId();
		}
			
		
		System.out.println("attempting to save...");
		System.out.println(sg);
		int courseId = sg.getCourse().getCourseCode();
				
		sgService.updateStudentGrade(sg);		
		
		return "redirect:/facultypage/grades?id=" + courseId;
	}
	
	@GetMapping("/gpasummary")
	public ModelAndView viewCgpaSummary () {
		List queryResult = sgService.cGpaSummary();
		
		List<ViewBagCgpa> vgLst = new ArrayList<ViewBagCgpa> ();
		
		for (Object subGrp : queryResult) {
			Object[] values = (Object[]) subGrp;
			String student = (String) values[0];
			
			if (values[1] == null) {
				values[1] = 0.0;
			}
			
			double grade = (double) values[1];			
			
			ViewBagCgpa vg = new ViewBagCgpa(student, grade);
			vgLst.add(vg);
		}
		
		ModelAndView mv = new ModelAndView("/Grades/cgpa");
		mv.addObject("viewbags", vgLst);
		
		return mv;
	}
	
	@GetMapping("/report")
	public ModelAndView createReport(Principal principal) {

		List<Course> courses = fService.getAllCourses(principal);
		
		String[] gradeRange = {"50-100", "0-49"};
		
		ModelAndView mv = new ModelAndView("/Grades/gradeReportForm");
		mv.addObject("checkedCourses", courses);
		mv.addObject("selectedGradeRange", gradeRange);

		return mv;
	}
	
	@PostMapping("/retrievereport")
	public ModelAndView retrieveReport(@RequestParam String selectedGradeRange, @RequestParam String[] checkedCourses, Principal principal) {
		//System.out.println(selectedGradeRange);
		//0-50
		//Arrays.asList(checkedCourses).forEach(System.out::println);
		//1001,1002
		
		String[] range = selectedGradeRange.split("-");
		int min = Integer.parseInt(range[0]);
		int max = Integer.parseInt(range[1]);	
		System.out.println("min: " + min + "max: " + max);
		List<Integer> courseIds = Arrays.asList(checkedCourses).stream()
				.map(Integer::parseInt)
				.collect(Collectors.toList());
		
		List<StudentGrade> resultGrades = sgService.filterGradesByRangeAndCourse(min, max, courseIds, principal);
				
		List<Student> resultStudents = resultGrades.stream()
				.map(g -> g.getStudent())
				.collect(Collectors.toList());
		
		List<ViewBagStudentGradeAndStudent> vbs = new ArrayList<ViewBagStudentGradeAndStudent> ();
		
		StudentGrade[] gradesArr = resultGrades.stream().toArray(StudentGrade[]::new);
		Student[] studentArr = resultStudents.stream().toArray(Student[]::new);
		
		for (int i = 0; i < gradesArr.length; i++) {
			ViewBagStudentGradeAndStudent vb = new ViewBagStudentGradeAndStudent(studentArr[i], gradesArr[i]);
			vbs.add(vb);
		}
		
		System.out.println("vbs size is: " + vbs.size());
		
		
		ModelAndView mv = new ModelAndView("/Grades/gradespage");
		mv.addObject("viewbags", vbs);
		return mv;
	}
	
}
