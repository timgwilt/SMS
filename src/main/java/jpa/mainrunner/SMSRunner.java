package jpa.mainrunner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

public class SMSRunner {
    private final Scanner sin;
    private final StringBuilder sb;

    private final CourseService courseService;
    private final StudentService studentService;
    private Student currentStudent;

    public SMSRunner() {
        sin = new Scanner(System.in);
        sb = new StringBuilder();
        courseService = new CourseService();
        studentService = new StudentService();
    }

    public static void main(String[] args) {
        SMSRunner sms = new SMSRunner();
        sms.run();
    }

    private void run() {
        // Login or quit
        switch (menu1()) {
            case 1:
                if (studentLogin()) {
                    registerMenu();
                }
                break;
            case 2:
                out.println("\nGoodbye!");
                break;
            default:
        }
    }

    private int menu1() {
        boolean validSelection = false;
        int menuSelection = 0;
        while (!validSelection) {
            sb.append("\n1. Student Login\n2. Quit Application\nPlease Enter Selection: ");
            out.print(sb);
            sb.delete(0, sb.length());
            try {
                menuSelection = sin.nextInt();
                if (menuSelection == 1 || menuSelection == 2) validSelection = true;
            } catch (Exception e) {
                validSelection = false;
                sin.next();
            }
        }
        return menuSelection;
    }

    private boolean studentLogin() {
        boolean retValue = false;
        boolean notLoggedIn = true;
        while (notLoggedIn) {
            out.print("Enter your email address: ");
            String email = sin.next();
            out.print("Enter your password: ");
            String password = sin.next();
            retValue = studentService.validateStudent(email, password);

            if (retValue) {
                notLoggedIn = !notLoggedIn;
                currentStudent = studentService.getStudentByEmail(email);
                List<Course> courses = studentService.getStudentCourses(email);
                out.println("\nMy Classes");
                out.printf("%-5s%-35s%-25s\n", "ID", "Course", "Instructor");
                for (Course course : courses) {
                    out.printf("%-5s%-35s%-25s\n", course.getCId(), course.getCName(), course.getCInstructorName());
                }
            } else {
                out.println("Wrong Credentials. Please try again.\n");
            }
        }
        return retValue;
    }

    private void registerMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            sb.append("\n1. Register a class\n2. Drop a class\n3. Logout\nPlease Enter Selection: ");
            out.print(sb);
            sb.delete(0, sb.length());
            int menuSelection = 0;
            try {
                menuSelection = sin.nextInt();
            } catch (Exception e) {
                out.println("You have made an incorrect selection. Try again!\n");
                sin.next();
                continue;
            }
            if (menuSelection < 1 || menuSelection > 3) {
                out.println("You have made an incorrect selection. Try again!\n");
                continue;
            }

            int number = 0;

            if (menuSelection < 3) {
                showCourseList(menuSelection);
                try {
                    number = sin.nextInt();
                } catch (Exception e) {
                    out.println("The selected course does not exist! Try again!\n");
                    sin.next();
                    continue;
                }
                boolean alreadyRegistered = studentService.getStudentRegistered(currentStudent.getSEmail(), number);
                Course course = courseService.getCourseById(number);
                if (course != null) {
                    switch (menuSelection) {
                        case 1:
                            if (!alreadyRegistered) {
                                studentService.registerStudentToCourse(currentStudent.getSEmail(), course.getCId());
                                out.printf("You have enrolled in course %s\n", course.getCName());
                                showStudentCourses(currentStudent);
                            } else {
                                out.println("You are already registered in that course!");
                            }
                            break;
                        case 2:
                            if (alreadyRegistered) {
                                studentService.unregisterStudentToCourse(currentStudent.getSEmail(), course.getCId());
                                out.printf("You have dropped course %s\n", course.getCName());
                                showStudentCourses(currentStudent);
                            } else {
                                out.printf("You have not registered for %s\n", course.getCName());
                            }
                            break;
                    }
                } else {
                    out.println("The selected course does not exist! Try again!");
                }
            } else {
                loggedIn = !loggedIn;
                out.println("\nGoodbye!");
            }
        }
    }

    private void showCourseList(int menuSelection) {
        List<Course> allCourses = courseService.getAllCourses();
        List<Course> studentCourses = studentService.getStudentCourses(currentStudent.getSEmail());
        switch (menuSelection) {
            case 1:
                if (studentCourses.size() > 0) allCourses.removeAll(studentCourses);
                out.println();
                out.printf("%-5s%-35s%-25s\n", "ID", "Course", "Instructor");
                for (Course course : allCourses) {
                    out.printf("%-5s%-35s%-25s\n", course.getCId(), course.getCName(), course.getCInstructorName());
                }
                break;
            case 2:
                out.println("My Classes");
                out.printf("%-5s%-35s%-25s\n", "ID", "Course", "Instructor");
                for (Course course : studentCourses) {
                    out.printf("%-5s%-35s%-25s\n", course.getCId(), course.getCName(), course.getCInstructorName());
                }
        }

        out.println();
        out.print("Enter Course Number: ");
    }

    private void showStudentCourses(Student temp) {
        StudentService scService = new StudentService();
        List<Course> sCourses = scService.getStudentCourses(temp.getSEmail());

        out.println("\nMy Classes");
        out.printf("%-5s%-35s%-25s\n", "ID", "Course", "Instructor");
        for (Course course : sCourses) {
            out.printf("%-5s%-35s%-25s\n", course.getCId(), course.getCName(), course.getCInstructorName());
        }
    }
}
