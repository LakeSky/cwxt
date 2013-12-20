package com.kzh.hibernate;

import com.kzh.system.security.dao.ResourceDao;
import com.kzh.test.hibernate.entity.Course;
import com.kzh.test.hibernate.entity.Student;
import com.kzh.test.hibernate.service.HibernateService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ContextConfiguration(value = "classpath:applicationContext.xml")
@Transactional
public class ManyToMany extends AbstractJUnit4SpringContextTests {
    @Autowired
    private HibernateService service;

    @Autowired
    private ResourceDao resourceDao;

    @Test
    public void importSeven() {
        Student student = new Student();
        student.setName("shijie");
        Course course = new Course();
        /*course.setName("course");
        Set<Course> courseSet = new HashSet<Course>();
        courseSet.add(course);
        student.setCourses(courseSet);*/


        service.save(student);
    }

    @Test
    public void importCourse() {

        Course course = new Course();
        course.setName("生物学");
        service.save(course);
    }

    @Test
    public void saveOrUpdate() {
        Course course = (Course) service.getEntity(Course.class, "8aa20d03428e5d2801428e5d2bde0000");
        //Course course = service
        System.out.println(course.getName());
        course.setName("化学1");
        Set<Student> studentSet = course.getStudents();

        service.save(course);
    }

    @Test
    public void queryAllMenu() {
        List list = resourceDao.queryMenuTree("4028e5f0424b088401424b0a17530000");
        System.out.println(list.size());
    }
}
