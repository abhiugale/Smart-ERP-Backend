package com.jspm.SmartErp.repository;
import com.jspm.SmartErp.model.Exam;
import com.jspm.SmartErp.model.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
    List<Exam> findByCourse_CourseId(Integer courseId);
    List<Exam> findByExamType(ExamType examType);
    List<Exam> findByExamDateBetween(LocalDate startDate, LocalDate endDate);
}
