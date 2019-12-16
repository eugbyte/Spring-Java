package sms.csv;
 
import java.io.PrintWriter;
import java.util.List;
 
import sms.model.Student;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
 
public class BeanToCsv {
 
  public static void export (PrintWriter writer,List<Student> students) {
    try {

      /* does not work
      ColumnPositionMappingStrategy<Student> mappingStrategy = new ColumnPositionMappingStrategy<Student>();
      String[] CSV_HEADER = { "id","degree","gender","mobile","semester","name"};
      mappingStrategy.setType(Student.class);
      mappingStrategy.setColumnMapping(CSV_HEADER);
      */
      
      StatefulBeanToCsv<Student> beanToCsv = new StatefulBeanToCsvBuilder<Student>(writer)	  
              .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
              .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
              .build();
 
      beanToCsv.write(students);
      
      System.out.println("Finished writing");      
    } catch (Exception e) {
      System.out.println("CSV error");
      e.printStackTrace();
    } finally {
    	writer.close();
    }
  }
}