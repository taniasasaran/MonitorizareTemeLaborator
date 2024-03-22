package ssvv.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.Validator;

public class ServiceTest {

    static Service service;
    @BeforeAll
    static void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "D:\\Documents\\sem6\\MonitorizareTemeLaboratorV01\\src\\test\\java\\ssvv\\example\\files\\studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "D:\\Documents\\sem6\\MonitorizareTemeLaboratorV01\\src\\test\\java\\ssvv\\example\\files\\teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "D:\\Documents\\sem6\\MonitorizareTemeLaboratorV01\\src\\test\\java\\ssvv\\example\\files\\note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Iterable<Student> students = service.findAllStudents();
        for (Student student : students) {
            service.deleteStudent(student.getID());
        }
    }

    @Test
    public void testAddStudentValidId() {
        int result = service.saveStudent("5", "Mihaela", 936);

        assert result == 0;
    }

    @Test
    public void testAddStudentInvalidId() {
        int result = service.saveStudent("", "Alex", 345);

        assert result == 1;
    }
}
