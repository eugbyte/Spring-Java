package sms.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sms.model.StudentGrade;

@Component
public class StudentGradeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return StudentGrade.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors error) {
		StudentGrade sg = (StudentGrade) obj;
		
		//null value for grades allowed for students who have courses approved, but not yet taken exams
		//however, string value for grades are not allowed
		if (sg.getGrade() == null) {
			//pass
		} else if ( Double.isNaN(sg.getGrade()) ) {
			error.rejectValue("grade", "grade.type.isString");
		}
	}

	
}
