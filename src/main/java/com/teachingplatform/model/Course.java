package com.teachingplatform.model;

// placeholder for the Course Model should be replaced by the team m
// member responsible for this.
public class Course {

    private Long id;
    private String courseTitle;
    private String courseCode;
    private String term;

    public Course(Long id, String courseTitle, String courseCode, String term) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.courseCode = courseCode;
        this.term = term;
    }

    // Getters (required for JSP to access these properties)
    public Long getCourseId() {
        return id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTerm() {
        return term;
    }
}
