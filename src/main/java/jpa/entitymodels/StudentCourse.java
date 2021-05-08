package jpa.entitymodels;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student_course", uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id", "student_email"})})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentCourse implements Serializable {
    static final long serialVersionUID = 1L;
    @Id
    @Column(name = "student_email", nullable = false, columnDefinition = "VARCHAR(50)", length = 50)
    String student_email;
    @Id
    int course_id;
}
