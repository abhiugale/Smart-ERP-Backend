// ExamServiceImpl.java (Fixed version)
package com.jspm.SmartErp.service;

import com.jspm.SmartErp.dto.CourseDTO;
import com.jspm.SmartErp.dto.ExamDTO;
import com.jspm.SmartErp.dto.ExamRequestDTO;
import com.jspm.SmartErp.model.Course;
import com.jspm.SmartErp.model.Exam;
import com.jspm.SmartErp.model.ExamType;
import com.jspm.SmartErp.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final CourseService courseService;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository, CourseService courseService) {
        this.examRepository = examRepository;
        this.courseService = courseService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getAllExams() {
        return examRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ExamDTO getExamById(Integer id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
        return convertToDTO(exam);
    }

    @Override
    public ExamDTO createExam(ExamRequestDTO examRequestDTO) {
        // Validate if exam already exists for the same course and date
        if (existsByCourseAndDate(examRequestDTO.getCourse().getCourseId(), examRequestDTO.getExamDate())) {
            throw new RuntimeException("Exam already exists for this course on the selected date");
        }

        // Validate course exists
        Course course = courseService.getCourseEntityById(examRequestDTO.getCourse().getCourseId());

        Exam exam = new Exam();

        // Convert String to ExamType enum
        try {
            ExamType examType = ExamType.valueOf(examRequestDTO.getExamType());
            exam.setExamType(examType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid exam type: " + examRequestDTO.getExamType());
        }

        exam.setExamDate(examRequestDTO.getExamDate());
        exam.setStartTime(examRequestDTO.getStartTime());
        exam.setEndTime(examRequestDTO.getEndTime());
        exam.setRoomNumber(examRequestDTO.getRoomNumber());
        exam.setTotalMarks(examRequestDTO.getTotalMarks());
        exam.setCourse(course);

        Exam savedExam = examRepository.save(exam);
        return convertToDTO(savedExam);
    }

    @Override
    public ExamDTO updateExam(Integer id, ExamRequestDTO examRequestDTO) {
        Exam existingExam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));

        // Check if updating to a date that conflicts with another exam (excluding current exam)
        boolean conflictExists = examRepository.findByCourseCourseId(examRequestDTO.getCourse().getCourseId())
                .stream()
                .filter(exam -> !exam.getExamId().equals(id))
                .anyMatch(exam -> exam.getExamDate().equals(examRequestDTO.getExamDate()));

        if (conflictExists) {
            throw new RuntimeException("Another exam already exists for this course on the selected date");
        }

        // Update course if changed
        if (existingExam.getCourse() == null ||
                !existingExam.getCourse().getCourseId().equals(examRequestDTO.getCourse().getCourseId())) {
            Course course = courseService.getCourseEntityById(examRequestDTO.getCourse().getCourseId());
            existingExam.setCourse(course);
        }

        // Convert String to ExamType enum
        try {
            ExamType examType = ExamType.valueOf(examRequestDTO.getExamType());
            existingExam.setExamType(examType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid exam type: " + examRequestDTO.getExamType());
        }

        existingExam.setExamDate(examRequestDTO.getExamDate());
        existingExam.setStartTime(examRequestDTO.getStartTime());
        existingExam.setEndTime(examRequestDTO.getEndTime());
        existingExam.setRoomNumber(examRequestDTO.getRoomNumber());
        existingExam.setTotalMarks(examRequestDTO.getTotalMarks());

        Exam updatedExam = examRepository.save(existingExam);
        return convertToDTO(updatedExam);
    }

    @Override
    public void deleteExam(Integer id) {
        if (!examRepository.existsById(id)) {
            throw new RuntimeException("Exam not found with id: " + id);
        }
        examRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByCourse(Integer courseId) {
        return examRepository.findByCourseCourseId(courseId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByExamType(String examType) {
        try {
            ExamType enumExamType = ExamType.valueOf(examType);
            return examRepository.findByExamType(enumExamType)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid exam type: " + examType);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByDateRange(LocalDate startDate, LocalDate endDate) {
        return examRepository.findByExamDateBetween(startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getUpcomingExams() {
        return examRepository.findByExamDateAfter(LocalDate.now())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByDepartment(String department) {
        return examRepository.findByCourseDepartment(department)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsBySemester(Integer semester) {
        return examRepository.findByCourseSemester(semester)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCourseAndDate(Integer courseId, LocalDate examDate) {
        return examRepository.existsByCourseAndDate(courseId, examDate);
    }

    private ExamDTO convertToDTO(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setExamId(exam.getExamId());
        dto.setExamType(exam.getExamType().name()); // Convert enum to string
        dto.setExamDate(exam.getExamDate());
        dto.setStartTime(exam.getStartTime());
        dto.setEndTime(exam.getEndTime());
        dto.setRoomNumber(exam.getRoomNumber());
        dto.setTotalMarks(exam.getTotalMarks());

        // Convert Course to CourseDTO
        if (exam.getCourse() != null) {
            Course course = exam.getCourse();
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseId(course.getCourseId());
            courseDTO.setCourseName(course.getCourseName());
            courseDTO.setDepartment(course.getDepartment());
            courseDTO.setSemester(course.getSemester());
            courseDTO.setDescription(course.getDiscription());
            dto.setCourse(courseDTO);
        }

        return dto;
    }
}