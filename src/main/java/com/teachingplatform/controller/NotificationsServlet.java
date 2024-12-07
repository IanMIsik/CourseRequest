package com.teachingplatform.controller;

import com.teachingplatform.dao.impl.NotificationDAOImpl;
import com.teachingplatform.dao.interfaces.NotificationDAO;
import com.teachingplatform.model.Notification;
import com.teachingplatform.service.NotificationService;
import com.teachingplatform.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/notifications")
public class NotificationsServlet extends HttpServlet {
    private NotificationService notificationService;

    @Override
    public void init() throws ServletException {

        DataSource dataSource = DatabaseConnection.lookupDataSource();

        NotificationDAO notificationDAO = new NotificationDAOImpl(dataSource);
        notificationService = new NotificationService(notificationDAO);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get current user from session
            //User currentUser = (User) request.getSession().getAttribute("currentUser");

//            // Fetch unread notifications
            List<Notification> unreadNotifications = notificationService
                .getNotificationsForUser(1, true);

            // Fetch read notifications (limited to last 20)
            List<Notification> readNotifications = notificationService
                .getNotificationsForUser(1, false)
                .stream()
                .limit(20)
                .collect(Collectors.toList());

            request.setAttribute("unreadNotifications", unreadNotifications);
            request.setAttribute("readNotifications", readNotifications);

            request.getRequestDispatcher("/WEB-INF/views/Notifications.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle errors
            request.setAttribute("errorMessage",URLEncoder.encode(e.getMessage(), "UTF-8"));
//            response.sendRedirect("error?message=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
             request.getRequestDispatcher("/WEB-INF/views/Notifications.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle marking all notifications as read
        try {
            int userId = 1; // change this to get the current user in the session as shown below
            //User currentUser = (User) request.getSession().getAttribute("currentUser");
            notificationService.markAllNotificationsAsRead(userId);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}