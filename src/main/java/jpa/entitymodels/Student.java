package jpa.entitymodels;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Student")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NamedQuery(query = "Select s from Student s", name = "allStudents")
public class Student {
    @Id
    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(50)", length = 50)
    String sEmail;
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(50)", length = 50)
    String sName;
    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(50)", length = 50)
    String sPass;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = {@JoinColumn(name = "student_email", referencedColumnName = "email",unique = false)},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id",unique = false)},
            indexes = {@Index(columnList = "student_email,course_id")}
    )
    List<Course> sCourses;

    public Student() {
        this.sEmail = "";
        this.sName = "";
        this.sPass = "";
    }

    public Student(String sEmail, String sName, String sPass) {
        this.sEmail = sEmail;
        this.sName = sName;
        this.sPass = sPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return sEmail.equals(student.sEmail) && sName.equals(student.sName) && sPass.equals(student.sPass);
    }
}
