package com.teachingplatform.service;


import com.teachingplatform.dao.interfaces.NotificationDAO;
import com.teachingplatform.model.Notification;

public class NotificationService {

    private NotificationDAO notificationDAO;

    public NotificationService(NotificationDAO notificationDAO){
        this.notificationDAO = notificationDAO;
    }

    public void sendNotification(int userId, String message) throws ServiceException{

        try{
            Notification notification = new Notification(userId, message);
            notificationDAO.addNotification(notification);
        }
        catch(Exception e){
            throw new ServiceException("Error Sending notification", e);
        }

    }
}
