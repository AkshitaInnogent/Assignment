package com.innogent.sms.repositories;

import com.innogent.sms.dtos.ResponseCourseDTO;
import com.innogent.sms.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c.c_name AS courseName, COUNT(s.id) AS studentCount FROM Course c LEFT JOIN c.students s GROUP BY c.c_name")
    List<Object[]> getStudentCountPerCourse();

    @Query("SELECT c FROM Course c LEFT JOIN c.students s WHERE s.id IS NULL")
    List<Course> getCoursesWithoutStudents();

    @Query(value = "SELECT c.id, c.c_name, c.course_instructor, COUNT(sc.student_id) AS studentCount FROM courses c LEFT JOIN student_course sc ON c.id = sc.course_id GROUP BY c.id, c.c_name, c.course_instructor ORDER BY studentCount DESC  LIMIT :n", nativeQuery = true)
    List<Object[]> getTopNCoursesNative(@Param("n") int n);


}
