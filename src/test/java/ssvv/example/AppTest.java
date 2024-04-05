package ssvv.example;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    Service service;


    @Before
    public void beforeAll() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "src/test/java/ssvv/example/files/studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "src/test/java/ssvv/example/files/teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "src/test/java/ssvv/example/files/note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Iterable<Student> students = service.findAllStudents();
        for (Student student : students) {
            service.deleteStudent(student.getID());
        }
    }

    @After
    public void afterAll() {
        // delete all students
        Iterable<Student> students = service.findAllStudents();
        List<Student> studentList = new ArrayList<>();
        students.forEach(studentList::add);
        for (Student student : studentList) {
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

    @Test
    public void testAddStudentValidGroup() {
        int result = service.saveStudent("6", "Andrei", 936);

        assert result == 0;
    }

    @Test
    public void testAddStudentInvalidGroup() {
        int result = service.saveStudent("7", "Mihai", 104);

        assert result == 1;
    }

    @Test
    public void testAddStudentValidName() {
        int result = service.saveStudent("8", "Alex", 916);

        assert result == 0;
    }

    @Test
    public void testAddStudentInvalidName() {
        int result = service.saveStudent("9", "", 234);

        assert result == 1;
    }

    @Test
    public void testAddStudentInvalidGroup2() {
        int result = service.saveStudent("23", "Oana", 209);

        assert result == 1;
    }

    //    BVA TESTS
    @Test
    public void testAddStudentValidGroupBVA1() {
        int result = service.saveStudent("10", "Alex", 111);

        assert result == 0;
    }

    @Test
    public void testAddStudentValidGroupBVA2() {
        int result = service.saveStudent("11", "Elisa", 937);

        assert result == 0;
    }

    @Test
    public void testAddStudentInvalidGroupBVA3() {
        int result = service.saveStudent("12", "Ianis", 110);

        assert result == 1;
    }

    @Test
    public void testAddStudentValidGroupBVA4() {
        int result = service.saveStudent("4", "Flavia", 112);

        assert result == 0;
    }

    @Test
    public void testAddStudentInvalidGroupBVA6() {
        int result = service.saveStudent("2", "Sorana", 938);

        assert result == 1;
    }
}
