package ssvv.example.validation;
import ssvv.example.domain.Student;

public class StudentValidator implements Validator<Student> {
    public void validate(Student student) throws ValidationException {
        if (student.getID() == null || student.getID().equals("")) {
            throw new ValidationException("ID invalid! \n");
        }
        if (student.getNume() == null || student.getNume().equals("")) {
            throw new ValidationException("Nume invalid! \n");
        }
        if (!String.valueOf(student.getGrupa()).matches("^[1-9][1-3][1-7]$")) {
            throw new ValidationException("Grupa invalida! \n");
        }
    }
}

