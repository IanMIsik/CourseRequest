package com.teachingplatform.model;

import java.time.LocalDateTime;

public class CourseRequest {

    private int id;
    private int courseId;
    private int applicantId;
    private String status;
    private LocalDateTime createdAt;

    // constructor
    public CourseRequest (int courseId, int applicantId){
        this.courseId = courseId;
        this.applicantId = applicantId;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId(){return id; }
    public void setId(int id) { this.id = id; }
    public int getCourseId() { return courseId; }
    public int getApplicantId() { return applicantId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }


}
