package com.innogent.sms.repositories;

import com.innogent.sms.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    @Query("SELECT s FROM Student s JOIN FETCH s.courses")
    List<Student> getAllStudents();

    @Query("SELECT s FROM Student s JOIN FETCH s.courses WHERE s.id = :id")
    Student getStudentById(int id);

    @Query("SELECT DISTINCT s FROM Student s JOIN s.courses cFilter JOIN FETCH s.courses cAll WHERE cFilter.c_name = :courseName ")
    Set<Student> findStudentsByCourseName(String courseName);

    @Query("SELECT DISTINCT s FROM Student s JOIN FETCH s.courses c WHERE s.city = :city AND c.course_instructor = :instructorName")
    List<Student> findStudentsByCityAndInstructor(String city, String instructorName);

    @Query("SELECT s FROM Student s LEFT JOIN s.courses c WHERE c.id IS NULL")
    List <Student> getStudentsWithoutCourses();
}
