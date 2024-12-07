package com.teachingplatform.service;


import com.teachingplatform.dao.interfaces.DAOException;
import com.teachingplatform.dao.interfaces.NotificationDAO;
import com.teachingplatform.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {

    private NotificationDAO notificationDAO;

    public NotificationService(NotificationDAO notificationDAO){
        this.notificationDAO = notificationDAO;
    }

    public void sendNotification(int userId, String message) throws ServiceException{

        try{
            Notification notification = new Notification(userId, message);
            notificationDAO.insertNotification(notification);
        }
        catch(Exception e){
            throw new ServiceException("Error Sending notification", e);
        }

    }

    public void addNotification(Notification notification) throws ServiceException, DAOException {

        try{
            notificationDAO.insertNotification(notification);
        }catch (Exception e){

            throw new ServiceException("Error adding Notification", e);

        }

    }

    public List<Notification> getNotificationsForUser(int userId, boolean unreadOnly) throws DAOException{

        List<Notification> userNotifications;

        try{

            userNotifications = notificationDAO.getNotificationsByUserId(userId, unreadOnly);

        }
        catch (Exception e){
            throw new DAOException("Failed to get user notifications", e);

        }
        return userNotifications;
    }

    public void markAllNotificationsAsRead(int userId) throws DAOException{

        try{
            List<Notification> unreadNotifications;
            unreadNotifications = notificationDAO.getNotificationsByUserId(userId, true);

            for( Notification notification: unreadNotifications){
                notificationDAO.markNotificationAsRead(notification.getId());
            }


        }
        catch(Exception e){
            throw new DAOException("Couldn't mark all notifications as read for some reason", e);
        }

    }
}
