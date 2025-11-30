package com.jspm.SmartErp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ai_predictions")
public class AIPrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer predictionId;

    private String predictedGrade;
    private Float predictionScore;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Constructors
    public AIPrediction() {}

    public AIPrediction(String predictedGrade, Float predictionScore, Student student) {
        this.predictedGrade = predictedGrade;
        this.predictionScore = predictionScore;
        this.student = student;
    }

    // Getters and Setters
    public Integer getPredictionId() { return predictionId; }
    public void setPredictionId(Integer predictionId) { this.predictionId = predictionId; }

    public String getPredictedGrade() { return predictedGrade; }
    public void setPredictedGrade(String predictedGrade) { this.predictedGrade = predictedGrade; }

    public Float getPredictionScore() { return predictionScore; }
    public void setPredictionScore(Float predictionScore) { this.predictionScore = predictionScore; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}