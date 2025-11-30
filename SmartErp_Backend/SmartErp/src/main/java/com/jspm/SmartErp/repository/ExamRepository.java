// ExamRepository.java
package com.jspm.SmartErp.repository;

import com.jspm.SmartErp.model.Exam;
import com.jspm.SmartErp.model.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    // Find exams by course
    List<Exam> findByCourseCourseId(Integer courseId);

    // Find exams by exam type
    List<Exam> findByExamType(ExamType examType);

    // Find exams by date range
    List<Exam> findByExamDateBetween(LocalDate startDate, LocalDate endDate);

    // Find upcoming exams
    List<Exam> findByExamDateAfter(LocalDate date);

    // Find exams by course department
    @Query("SELECT e FROM Exam e WHERE e.course.department = :department")
    List<Exam> findByCourseDepartment(@Param("department") String department);

    // Find exams by course semester
    @Query("SELECT e FROM Exam e WHERE e.course.semester = :semester")
    List<Exam> findByCourseSemester(@Param("semester") Integer semester);

    // Check if exam exists for course and date
    @Query("SELECT COUNT(e) > 0 FROM Exam e WHERE e.course.courseId = :courseId AND e.examDate = :examDate")
    boolean existsByCourseAndDate(@Param("courseId") Integer courseId, @Param("examDate") LocalDate examDate);
}