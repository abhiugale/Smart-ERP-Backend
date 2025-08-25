package com.jspm.SmartErp.repository;
import com.jspm.SmartErp.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findByStudent_StudentId(Integer studentId);
    List<Result> findByCourse_CourseId(Integer courseId);
}
