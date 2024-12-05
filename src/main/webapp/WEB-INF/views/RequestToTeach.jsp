<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Request to Teach Course</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%-- Extensive Debugging Section --%>
    <div class="container mt-3">
        <h3>Debug Information</h3>

        <%-- Check using pure Java --%>
        <%
            List availableCoursesList = (List) request.getAttribute("availableCourses");
            if (availableCoursesList == null) {
                out.println("<div class='alert alert-danger'>availableCourses is NULL in request attributes</div>");
            } else {
                out.println("<div class='alert alert-success'>availableCourses is NOT NULL. Size: " + availableCoursesList.size() + "</div>");
            }
        %>

    <div class="container mt-5">
        <h2 class="mb-4">Request to Teach Course</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>
        <%-- Add this right after the JSTL core taglib import --%>
        <c:if test="${empty availableCourses}">
            <div class="alert alert-warning" role="alert">
                No courses available. availableCourses is empty or null.
            </div>
        </c:if>

        <%-- Or add a debug section to show details --%>
        <c:if test="${not empty availableCourses}">
            <div class="alert alert-info" role="alert">
                Number of Courses: ${availableCourses.size()}
            </div>
        </c:if>


        <form action="SubmitCourseRequestServlet" method="post">
            <div class="mb-3">
                <label for="courseSelect" class="form-label">Select Course</label>
                <select class="form-select" id="courseSelect" name="courseId" required>
                    <option value="">Choose a Course</option>
                    <c:forEach var="course" items="${availableCourses}">
                        <option value="${course.id}">
                            ${course.courseTitle} (${course.courseCode}) - ${course.term}
                        </option>
                    </c:forEach>
                </select>
            </div>


            <div class="mb-3">
                <label for="teachingStatement" class="form-label">Why do you want to teach this course?</label>
                <textarea class="form-control" id="teachingStatement" name="teachingStatement" rows="4" required></textarea>
            </div>

            <div class="mb-3">
                <label for="qualifications" class="form-label">Relevant Qualifications</label>
                <textarea class="form-control" id="qualifications" name="qualifications" rows="3" required></textarea>
            </div>

            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" id="confirmEligibility" required>
                <label class="form-check-label" for="confirmEligibility">
                    I confirm that I meet the preferred qualifications for this course
                </label>
            </div>

            <button type="submit" class="btn btn-primary">Submit Teaching Request</button>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>