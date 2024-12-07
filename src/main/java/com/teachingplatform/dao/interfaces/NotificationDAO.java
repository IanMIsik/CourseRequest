package com.teachingplatform.dao.interfaces;
import com.teachingplatform.model.Notification;
import java.util.List;

public interface NotificationDAO {

    void insertNotification(Notification notification) throws DAOException;
    List<Notification> getNotificationsByUserId(int userId, boolean unreadOnly) throws DAOException;
    void markNotificationAsRead(int notificationId) throws DAOException;
//    List<Notification> getNotificationsByRecipient(int recipientId, boolean unreadOnly) throws DAOException;
}
