#SBA - Core Java/JPA/JUnit

###School Management System Project

####Basic Synopsis
    A valid student can login and schedule or drop a course.

####Basic Assumptions
* Only valid students can login.
* Validated students can enroll in a course.
* Students and Courses 
    * Can not be change through the program.
    * The program can only read from the Students and Courses table from the database.  
* MariaDB is the expected database
    * port 3306.
* Any valid student, verified from the Student table, can login and register for a course.
  * Students can not register for a course that they are already registered to.
* An option to drop a course is available also.
* Program run life
    * The program will run until the menu option to logout is selected.
* Handle as exceptions

###Basic output and Flow example
    This the minimum required workflow, this has been enhanced.

Are you a(n)

Student
quit
Please, enter 1 or 2.

1

Enter Your Email:

J@gmail.com

Enter Your Password:

333

My Classes:

ID   COURSE  INSTRUCTOR 

1    GYM     Mark
2    Math    Luke

1.Register to Class
2.Logout

1

All Courses:

ID COURSE    INSTRUCTOR 

1   GYM      Mark
2   Math     Luke
3   Science  Stephanie
4   English  Lisa

Which Course?

3

My Classes:

ID   COURSE     INSTRUCTOR

1    GYM        Mark
2    Math       Luke
3    Science    Stephanie

You have been signed out.