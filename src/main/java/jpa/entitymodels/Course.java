package jpa.entitymodels;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Course")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NamedQuery(query = "Select c from Course c", name = "allCourses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT(11) UNSIGNED")
    int cId;
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(50)", length = 50)
    String cName;
    @Column(name = "instructor", nullable = false, columnDefinition = "VARCHAR(50)", length = 50)
    String cInstructorName;

    public Course() {
        this.cName = "";
        this.cInstructorName = "";
    }

    public Course(String cName, String cInstructorName) {
        this.cName = cName;
        this.cInstructorName = cInstructorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return cId == course.cId && Objects.equals(cName, course.cName) && Objects.equals(cInstructorName, course.cInstructorName);
    }
}
