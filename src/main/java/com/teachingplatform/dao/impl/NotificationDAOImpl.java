package com.teachingplatform.dao.impl;

import com.teachingplatform.dao.interfaces.DAOException;
import com.teachingplatform.dao.interfaces.NotificationDAO;
import com.teachingplatform.model.Notification;

import javax.sql.DataSource;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO {

    private DataSource dataSource;

    public NotificationDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }


    @Override
    public void addNotification(Notification notification) throws DAOException {
        String sql = "INSERT INTO Notifications (recipientId, message, timestamp, isRead) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, notification.getRecipientId());
            pstmt.setString(2, notification.getMessage());
            pstmt.setTimestamp(3, Timestamp.valueOf(notification.getTimestamp()));
            pstmt.setBoolean(4, notification.isRead());

            pstmt.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    notification.setId(generatedKeys.getInt(1));
                }
            } catch(SQLException e){

                throw new DAOException("Error adding notification", e);
            }
        } catch (SQLException e) {
            throw new DAOException("Error adding notification", e);
        }
    }

    @Override
    public List<Notification> getNotificationsForUser(int userId, boolean unreadOnly) throws DAOException {
        List<Notification> notifications = new ArrayList<>();
        String sql = unreadOnly ? "SELECT * FROM Notifications WHERE recipientId = ? AND isRead = false ORDER BY timestamp DESC"
            : "SELECT * FROM Notifications WHERE recipientId = ? ORDER BY timestamp DESC";

        try( Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1, userId);

            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    Notification notification = new Notification(
                        rs.getInt("recipientId"),
                        rs.getString("message")
                    );

                    notification.setId(rs.getInt("id"));
                    if(!rs.getBoolean("isRead")){
                        notification.markAsRead();
                    }

                    notifications.add(notification);



                }
            }
            catch(SQLException e){

                throw new DAOException("Failed to get notifications", e);
            }
        }
        catch(SQLException e){
            throw new DAOException("Error Retrieving notifications", e);
        }
        return notifications ;
    }
}
