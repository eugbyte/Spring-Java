package sms.controllers;
 
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sms.csv.BeanToCsv;
import sms.model.Student;
import sms.repo.StudentRepository;
import sms.services.CourseService;
 
@RestController
@RequestMapping("/adminpage/courses")
public class CsvDownloadControllers {
  @Autowired
  StudentRepository studentRepository;
  
  @Autowired
  private CourseService cService;
 
  @GetMapping("/download")
  public void downloadCSV(HttpServletResponse response, @RequestParam int courseCode) throws IOException{
    response.setContentType("text/csv");
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.csv");
    
    List<Student> students = cService.retrieveStudentByCourseId(courseCode);
    
    System.out.println(students);
    
    BeanToCsv.export(response.getWriter(), students);
    //WriteDataToCSV.writeDataToCsvUsingStringArray(response.getWriter(), students);
  }
}