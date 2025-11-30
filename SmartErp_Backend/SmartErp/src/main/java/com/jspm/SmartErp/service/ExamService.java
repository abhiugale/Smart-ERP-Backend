// ExamService.java
package com.jspm.SmartErp.service;

import com.jspm.SmartErp.dto.ExamDTO;
import com.jspm.SmartErp.dto.ExamRequestDTO;

import java.time.LocalDate;
import java.util.List;

public interface ExamService {
    List<ExamDTO> getAllExams();
    ExamDTO getExamById(Integer id);
    ExamDTO createExam(ExamRequestDTO examRequestDTO);
    ExamDTO updateExam(Integer id, ExamRequestDTO examRequestDTO);
    void deleteExam(Integer id);
    List<ExamDTO> getExamsByCourse(Integer courseId);
    List<ExamDTO> getExamsByExamType(String examType);
    List<ExamDTO> getExamsByDateRange(LocalDate startDate, LocalDate endDate);
    List<ExamDTO> getUpcomingExams();
    List<ExamDTO> getExamsByDepartment(String department);
    List<ExamDTO> getExamsBySemester(Integer semester);
    boolean existsByCourseAndDate(Integer courseId, LocalDate examDate);
}