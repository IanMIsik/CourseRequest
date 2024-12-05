package com.teachingplatform.model;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private int recipientId;
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;

    public Notification(int recipientId, String message){
        this.recipientId = recipientId;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

       // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getRecipientId() { return recipientId; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isRead() { return isRead; }
    public void markAsRead() { this.isRead = true; }
}
