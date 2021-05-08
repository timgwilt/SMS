package jpa.service;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDAO {
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SMS");

    @Override
    @Transactional
    public List<Student> getAllStudents() {
        List<Student> students = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createNamedQuery("allStudents");
        students = query.getResultList();
        entityManager.close();
        return students;
    }

    @Override
    @Transactional
    public Student getStudentByEmail(String sEmail) {
        Student student = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createQuery("from Student s WHERE s.sEmail = :email ");
            query.setParameter("email", sEmail);
            student = (Student) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return student;
    }

    @Override
    @Transactional
    public boolean validateStudent(String sEmail, String sPassword) {
        Student student = getStudentByEmail(sEmail);
        if (student != null) {
            return student.getSPass().equals(sPassword);
        }
        return false;
    }


    @Override
    public void registerStudentToCourse(String sEmail, int cId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student student = entityManager.find(Student.class, sEmail);
            List<Course> courses = getStudentCourses(sEmail);

            List<Course> courseList = new ArrayList<Course>();
            courseList.addAll(courses);
            courseList.add(new CourseService().getCourseById(cId));

            if (courseList.size() > 0) {
                student.setSCourses(courseList);
                entityManager.persist(student);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    @Transactional
    public List<Course> getStudentCourses(String sEmail) {
        List<Course> courses = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNativeQuery("Select c.* from Course c JOIN student_course sc on c.id = sc.course_id WHERE sc.student_email = :email", Course.class);
            query.setParameter("email", sEmail);
            courses = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return courses;
    }

    public void unregisterStudentToCourse(String sEmail, int cId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student student = entityManager.find(Student.class, sEmail);
            List<Course> courses = getStudentCourses(sEmail);
            if (courses.size() > 0) {
                List<Course> courseList = new ArrayList<Course>();
                for (Course course : courses) {
                    if (course.getCId() != cId) {
                        courseList.add(course);
                    }
                }
                student.setSCourses(courseList);
                entityManager.persist(student);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Transactional
    public boolean getStudentRegistered(String sEmail, int cId) {
        Course course = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Query query = entityManager.createNativeQuery("Select c.* from Course c JOIN student_course sc on c.id = sc.course_id WHERE sc.student_email = :email and sc.course_id = :id", Course.class);
            query.setParameter("email", sEmail);
            query.setParameter("id", cId);
            course = (Course) query.getSingleResult();
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return course != null;
    }
}