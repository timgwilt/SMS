package jpa.service;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory( "SMS" );
    @BeforeEach
    void setUp() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Student firstStudent = entityManager.find(Student.class,"aiannitti7@is.gd");
        Student secondStudent = entityManager.find(Student.class,"cjaulme9@bing.com");

        Course course01 = entityManager.find(Course.class,1);
        Course course02 = entityManager.find(Course.class,2);

        List<Course> courseList01 = new ArrayList<Course>();
        courseList01.add(course01);
        List<Course> courseList02 = new ArrayList<Course>();
        courseList02.add(course02);

        firstStudent.setSCourses(courseList01);
        secondStudent.setSCourses(courseList02);

        entityManager.persist(firstStudent);
        entityManager.persist(secondStudent);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterEach
    void tearDown() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Student firstStudent = entityManager.find(Student.class,"aiannitti7@is.gd");
        Student secondStudent = entityManager.find(Student.class,"cjaulme9@bing.com");

        List<Course> courseList01 = new ArrayList<Course>();
        List<Course> courseList02 = new ArrayList<Course>();

        firstStudent.setSCourses(courseList01);
        secondStudent.setSCourses(courseList02);

        entityManager.persist(firstStudent);
        entityManager.persist(secondStudent);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    @DisplayName("Retrieving all students")
    void getAllStudents() {
        List<Student> expectedStudents = new ArrayList<Student>();
        Student firstStudent = new Student("aiannitti7@is.gd","Alexandra Iannitti", "TWP4hf5j");
        Student fourthStudent = new Student("cjaulme9@bing.com","Cahra Jaulme", "FnVklVgC6r6");
        Student secondStudent = new Student("cstartin3@flickr.com","Clem Startin", "XYHzJ1S");
        Student thirdStudent = new Student("hguerre5@deviantart.com","Harcourt Guerre", "OzcxzD1PGs");
        Student fifthStudent = new Student("hluckham0@google.ru","Hazel Luckham", "X1uZcoIh0dj");
        Student sixthStudent = new Student("htaffley6@columbia.edu","Holmes Taffley", "xowtOQ");
        Student seventhStudent = new Student("ljiroudek8@sitemeter.com","Laryssa Jiroudek", "bXRoLUP");
        Student eigthStudent = new Student("qllorens2@howstuffworks.com","Quillan Llorens", "W6rJuxd");
        Student ninthStudent = new Student("sbowden1@yellowbook.com","Sonnnie Bowden", "SJc4aWSU");
        Student tenthStudent = new Student("tattwool4@biglobe.ne.jp","Thornie Attwool", "Hjt0SoVmuBz");
        expectedStudents.add(firstStudent);
        expectedStudents.add(fourthStudent);
        expectedStudents.add(secondStudent);
        expectedStudents.add(thirdStudent);
        expectedStudents.add(fifthStudent);
        expectedStudents.add(sixthStudent);
        expectedStudents.add(seventhStudent);
        expectedStudents.add(eigthStudent);
        expectedStudents.add(ninthStudent);
        expectedStudents.add(tenthStudent);

        List<Student> actualStudents = new StudentService().getAllStudents();

        Object[] expectedArray = expectedStudents.toArray();
        Object[] actualArray = actualStudents.toArray();

        Assertions.assertArrayEquals(expectedArray,actualArray);

    }

    @Test
    @DisplayName("Getting a student by email")
    void getStudentByEmail() {
        Student expectedStudent = new Student("aiannitti7@is.gd","Alexandra Iannitti", "TWP4hf5j");

        Student actualStudent = new StudentService().getStudentByEmail("aiannitti7@is.gd");

        Assertions.assertEquals(expectedStudent,actualStudent);
    }

    @Test
    @Disabled
    void validateStudent() {
    }

    @Test
    @Disabled
    void registerStudentToCourse() {
    }

    @Test
    @DisplayName("Getting a student's courses")
    void getStudentCourses() {
        List<Course> expectedCourses = new ArrayList<Course>();
        Course course = new Course(1,"English","Anderea Scamaden");
        expectedCourses.add(course);

        List<Course> actualCourses = new StudentService().getStudentCourses("aiannitti7@is.gd");

        Assertions.assertArrayEquals((Object[]) expectedCourses.toArray(),(Object[]) actualCourses.toArray());
    }
}