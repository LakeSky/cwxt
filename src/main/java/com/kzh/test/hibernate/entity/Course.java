package com.kzh.test.hibernate.entity;

import com.kzh.generate.common.Edit;
import com.kzh.generate.common.Name;
import com.kzh.generate.common.Show;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    private String id;

    //(cascade = javax.persistence.CascadeType.REMOVE)
    @ManyToMany(targetEntity = Student.class)
/*    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))*/
    @JoinTable(name = "student_course")
    private Set<Student> students;
    @Name("名称")
    @Show
    @com.kzh.generate.common.Query
    @Edit
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
