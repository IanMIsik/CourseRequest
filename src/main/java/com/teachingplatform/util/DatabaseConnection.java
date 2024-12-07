//package com.teachingplatform.util;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import javax.sql.DataSource;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Properties;
//
//public class DatabaseConnection {
//
//    private static HikariDataSource dataSource;
//
//    static {
//        try{
//
//            Properties props = new Properties();
//            try(InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")){
//                props.load(input);
//            } catch(Exception e){
//                e.printStackTrace();
//                throw new RuntimeException("Error initializing database connection", e);
//            }
//                        // Configure HikariCP
//            HikariConfig config = new HikariConfig();
//            config.setJdbcUrl(props.getProperty("db.url"));
//            config.setUsername(props.getProperty("db.username"));
//            config.setPassword(props.getProperty("db.password"));
//            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//
//            // Additional connection pool settings
//            config.setMaximumPoolSize(10);
//            config.setMinimumIdle(5);
//            config.setIdleTimeout(30000);
//            config.setMaxLifetime(2000000);
//            config.setConnectionTimeout(30000);
//
//            // Create data source
//            dataSource = new HikariDataSource(config);
//            //
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            throw new RuntimeException("Error initializing database connection", e);
//        }
//    }
//    public static Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
//
//    public static void closeDataSource() {
//        if (dataSource != null) {
//            dataSource.close();
//        }
//    }
//    public static DataSource lookupDataSource(){
//        return dataSource;
//    }
//
//
//    public static void initializeDatabase() {
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement()) {
//
//            // Create Users Table
//            stmt.execute("CREATE TABLE IF NOT EXISTS Users (" +
//                "id INT AUTO_INCREMENT PRIMARY KEY, " +
//                "name VARCHAR(100), " +
//                "email VARCHAR(100) UNIQUE, " +
//                "password VARCHAR(255), " +
//                "user_type ENUM('ACADEMIC_PROFESSIONAL', 'ACADEMIC_INSTITUTION')" +
//                ")");
//
//            // Create Courses Table
//            stmt.execute("CREATE TABLE IF NOT EXISTS Courses (" +
//                "id INT AUTO_INCREMENT PRIMARY KEY, " +
//                "course_title VARCHAR(200), " +
//                "course_code VARCHAR(50), " +
//                "term VARCHAR(50), " +
//                "owner_id INT, " +
//                "FOREIGN KEY (owner_id) REFERENCES Users(id)" +
//                ")");
//
//            // Create Requests Table
//            stmt.execute("CREATE TABLE IF NOT EXISTS Requests (" +
//                "id INT AUTO_INCREMENT PRIMARY KEY, " +
//                "course_id INT, " +
//                "applicant_id INT, " +
//                "status ENUM('PENDING', 'ACCEPTED', 'REJECTED'), " +
//                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
////                "FOREIGN KEY (course_id) REFERENCES Courses(id), " +
////                "FOREIGN KEY (applicant_id) REFERENCES Users(id)" +
//                ")");
//
//            // Create Notifications Table
//            stmt.execute("CREATE TABLE IF NOT EXISTS Notifications (" +
//                "id INT AUTO_INCREMENT PRIMARY KEY, " +
//                "recipient_id INT, " +
//                "message TEXT, " +
//                "is_read BOOLEAN DEFAULT FALSE, " +
//                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
////                "FOREIGN KEY (recipient_id) REFERENCES Users(id)" +
//                ")");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error initializing database tables", e);
//        }
//    }
//}
package com.teachingplatform.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {

    private static HikariDataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
                props.load(input);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error initializing database connection", e);
            }

            // Configure HikariCP for PostgreSQL
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.username"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName("org.postgresql.Driver");

            // Additional connection pool settings
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(2000000);
            config.setConnectionTimeout(30000);

            // Create data source
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing database connection", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public static DataSource lookupDataSource() {
        return dataSource;
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Create Users Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "email VARCHAR(100) UNIQUE, " +
                "password VARCHAR(255), " +
                "user_type VARCHAR(50)" +
                ")");

            // Create Courses Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Courses (" +
                "id SERIAL PRIMARY KEY, " +
                "course_title VARCHAR(200), " +
                "course_code VARCHAR(50), " +
                "term VARCHAR(50), " +
                "owner_id INT, " +
                "FOREIGN KEY (owner_id) REFERENCES Users(id)" +
                ")");

            // Create Requests Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Requests (" +
                "id SERIAL PRIMARY KEY, " +
                "course_id INT, " +
                "applicant_id INT, " +
                "status VARCHAR(20), " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ")");

            // Create Notifications Table
            stmt.execute("CREATE TABLE IF NOT EXISTS Notifications (" +
                "id SERIAL PRIMARY KEY, " +
                "recipient_id INT, " +
                "message TEXT, " +
                "is_read BOOLEAN DEFAULT FALSE, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing database tables", e);
        }
    }
}