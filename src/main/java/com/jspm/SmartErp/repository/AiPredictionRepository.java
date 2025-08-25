package com.jspm.SmartErp.repository;
import com.jspm.SmartErp.model.AiPrediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiPredictionRepository extends JpaRepository<AiPrediction, Integer> {
    List<AiPrediction> findByStudent_StudentId(Integer studentId);
}
