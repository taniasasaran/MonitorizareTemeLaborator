package ssvv.example;


import org.junit.*;
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
public class IntegrationTest {
    static Service service;

    @BeforeClass
    public static void beforeAll() {
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
        Iterable<Tema> teme = service.findAllTeme();
        for (Tema tema : teme) {
            service.deleteTema(tema.getID());
        }
        Iterable<Nota> note = service.findAllNote();
        for (Nota nota : note) {
            service.deleteNota(nota.getID().getObject1(), nota.getID().getObject2());
        }
    }

    @AfterClass
    public static void afterAll() {
        // delete all students
        Iterable<Student> students = service.findAllStudents();
        List<Student> studentList = new ArrayList<>();
        students.forEach(studentList::add);
        for (Student student : studentList) {
            service.deleteStudent(student.getID());
        }

        // delete all assignments
        Iterable<Tema> teme = service.findAllTeme();
        List<Tema> temaList = new ArrayList<>();
        teme.forEach(temaList::add);
        for (Tema tema : temaList) {
            service.deleteTema(tema.getID());
        }

        // delete all grades
        Iterable<Nota> note = service.findAllNote();
        List<Nota> notaList = new ArrayList<>();
        note.forEach(notaList::add);
        for (Nota nota : notaList) {
            service.deleteNota(nota.getID().getObject1(), nota.getID().getObject2());
        }
    }
    // big bang integration
    @Test
    public void testAddStudentIntegration() {
        int result = service.saveStudent("99", "Olivia", 312);

        assert result == 0;
    }

    @Test
    public void testAddAssignmentIntegration() {
        int result = service.saveTema("99", "Tema99", 8, 5);

        assert result == 0;
    }

    @Test
    public void testAddGradeIntegration() {
        int result = service.saveNota("99", "99", 10, 6, "Good job!");

        assert result == 0;
    }

    @Test
    public void testAddStudentAssignmentGradeIntegration() {
        int resultS = service.saveStudent("100", "Ada", 936);
        int resultT = service.saveTema("100", "Tema1", 5, 3);
        int resultN = service.saveNota("100", "100", 10, 5, "Good job!");

        assert resultS == 0 && resultT == 0 && resultN == 0;
    }

    // incremental integration
    @Test
    public void testAddStudent() {
        int result = service.saveStudent("1", "Paul", 936);

        assert result == 0;
    }

    @Test
    public void testAddStudentAssignment() {
        int resultS = service.saveStudent("2", "Tudor", 936);
        int resultT = service.saveTema("2", "Tema1", 5, 3);

        assert resultS == 0 && resultT == 0;
    }

    @Test
    public void testAddStudentAssignmentGrade() {
        int resultS = service.saveStudent("3", "Oana", 936);
        int resultT = service.saveTema("3", "Tema1", 5, 3);
        int resultN = service.saveNota("3", "3", 10, 5, "Good job!");

        assert resultS == 0 && resultT == 0 && resultN == 0;
    }
}
