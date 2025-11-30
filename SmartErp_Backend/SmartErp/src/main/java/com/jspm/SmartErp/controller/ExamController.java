// ExamController.java
package com.jspm.SmartErp.controller;

import com.jspm.SmartErp.dto.ExamDTO;
import com.jspm.SmartErp.dto.ExamRequestDTO;
import com.jspm.SmartErp.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        try {
            List<ExamDTO> exams = examService.getAllExams();
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExamById(@PathVariable Integer id) {
        try {
            ExamDTO exam = examService.getExamById(id);
            return ResponseEntity.ok(exam);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> createExam(@RequestBody ExamRequestDTO examRequestDTO) {
        try {
            // Validate required fields
            if (examRequestDTO.getExamType() == null || examRequestDTO.getExamDate() == null ||
                    examRequestDTO.getCourse() == null || examRequestDTO.getCourse().getCourseId() == null) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Exam type, date, and course are required");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            ExamDTO createdExam = examService.createExam(examRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExam);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to create exam");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateExam(@PathVariable Integer id, @RequestBody ExamRequestDTO examRequestDTO) {
        try {
            // Validate required fields
            if (examRequestDTO.getExamType() == null || examRequestDTO.getExamDate() == null ||
                    examRequestDTO.getCourse() == null || examRequestDTO.getCourse().getCourseId() == null) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Exam type, date, and course are required");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            ExamDTO updatedExam = examService.updateExam(id, examRequestDTO);
            return ResponseEntity.ok(updatedExam);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to update exam");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable Integer id) {
        try {
            examService.deleteExam(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to delete exam");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Additional endpoints for filtering
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ExamDTO>> getExamsByCourse(@PathVariable Integer courseId) {
        try {
            List<ExamDTO> exams = examService.getExamsByCourse(courseId);
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/type/{examType}")
    public ResponseEntity<List<ExamDTO>> getExamsByExamType(@PathVariable String examType) {
        try {
            List<ExamDTO> exams = examService.getExamsByExamType(examType);
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ExamDTO>> getExamsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<ExamDTO> exams = examService.getExamsByDateRange(startDate, endDate);
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ExamDTO>> getUpcomingExams() {
        try {
            List<ExamDTO> exams = examService.getUpcomingExams();
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<ExamDTO>> getExamsByDepartment(@PathVariable String department) {
        try {
            List<ExamDTO> exams = examService.getExamsByDepartment(department);
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<ExamDTO>> getExamsBySemester(@PathVariable Integer semester) {
        try {
            List<ExamDTO> exams = examService.getExamsBySemester(semester);
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/check-conflict")
    public ResponseEntity<Map<String, Boolean>> checkExamConflict(
            @RequestParam Integer courseId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate examDate) {
        try {
            boolean exists = examService.existsByCourseAndDate(courseId, examDate);
            Map<String, Boolean> response = new HashMap<>();
            response.put("conflictExists", exists);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}