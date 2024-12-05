package com.teachingplatform.config;



import com.teachingplatform.util.DatabaseConnection;
//import javax.servlet.ServletContextListener;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.annotation.WebListener;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DatabaseInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Initialize database tables when the application starts
            DatabaseConnection.initializeDatabase();
            System.out.println("Database initialized successfully");
        } catch (Exception e) {
            System.err.println("Failed to initialize database");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Close database connection pool when application shuts down
        DatabaseConnection.closeDataSource();
    }
}