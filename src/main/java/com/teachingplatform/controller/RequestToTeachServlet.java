package com.teachingplatform.controller;


import com.teachingplatform.dao.impl.NotificationDAOImpl;
import com.teachingplatform.dao.impl.RequestDAOImpl;
import com.teachingplatform.dao.interfaces.NotificationDAO;
import com.teachingplatform.dao.interfaces.RequestDAO;
import com.teachingplatform.model.Course;
import com.teachingplatform.model.CourseRequest;
import com.teachingplatform.service.NotificationService;
import com.teachingplatform.service.RequestService;
import com.teachingplatform.util.DatabaseConnection;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/SubmitCourseRequestServlet")
public class RequestToTeachServlet extends HttpServlet {
    private RequestService requestService;
    private NotificationService notificationService;


    @Override
    public void init() throws ServletException {

        DataSource dataSource = DatabaseConnection.lookupDataSource();

        RequestDAO requestDAO = new RequestDAOImpl(dataSource);

        // Initialize services
        requestService = new RequestService(requestDAO);


        NotificationDAO notificationDAO = new NotificationDAOImpl(dataSource);
        notificationService = new NotificationService(notificationDAO);



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch available courses (Should be edited to use the course request service)
        List<Course> availableCourses = List.of(
                new Course(1L, "Introduction to Computer Science", "CS101", "Fall 2024"),
                new Course(2L, "Data Structures and Algorithms", "CS202", "Spring 2024"),
                new Course(3L, "Web Development Fundamentals", "WEB301", "Summer 2024"),
                new Course(4L, "Database Management Systems", "CS305", "Fall 2024"),
                new Course(5L, "Software Engineering", "CS401", "Spring 2025")
            );
        request.setAttribute("availableCourses", availableCourses);

        request.getRequestDispatcher("/WEB-INF/views/RequestToTeach.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get current user from session
            //User currentUser = (User) request.getSession().getAttribute("currentUser");

            // using user id 1 for testing
            // Get user id from the session as shown above
            int userId = 1;
            // Create course request
            CourseRequest courseRequest = new CourseRequest(
                Integer.parseInt(request.getParameter("courseId")),
                userId
            );


            // Submit request
            requestService.submitRequest(courseRequest);
            notificationService.sendNotification(userId, "A request to teach course with id "+courseRequest.getCourseId()+" by applicant with user ID "+courseRequest.getApplicantId()+" has been submitted.");



            // Redirect with success message
            response.sendRedirect("notifications");
        } catch (Exception e) {
            // Handle errors
            request.setAttribute("errorMessage", "Failed to submit request: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/RequestToTeach.jsp").forward(request, response);
        }
    }
}