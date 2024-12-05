package com.teachingplatform.dao.interfaces;
import com.teachingplatform.model.Notification;
import java.util.List;

public interface NotificationDAO {

    void addNotification(Notification notification) throws DAOException;
    List<Notification> getNotificationsForUser(int userId, boolean unreadOnly) throws DAOException;
}
