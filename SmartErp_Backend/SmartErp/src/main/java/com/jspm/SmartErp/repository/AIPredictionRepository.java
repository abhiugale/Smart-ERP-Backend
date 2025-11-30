package com.jspm.SmartErp.repository;

import com.jspm.SmartErp.model.AIPrediction;
import com.jspm.SmartErp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AIPredictionRepository extends JpaRepository<AIPrediction, Integer> {
    List<AIPrediction> findByStudent(Student student);
    Optional<AIPrediction> findTopByStudentOrderByPredictionIdDesc(Student student);
}