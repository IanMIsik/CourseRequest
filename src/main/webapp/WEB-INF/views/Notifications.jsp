<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Notifications</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-8 offset-md-2">
                <h2 class="mb-4">My Notifications</h2>

                <div class="d-flex justify-content-between mb-3">
                    <h5>Unread Notifications</h5>
                    <button id="markAllRead" class="btn btn-sm btn-outline-secondary">Mark All as Read</button>
                </div>

                <c:choose>
                    <c:when test="${not empty unreadNotifications}">
                        <div class="list-group">
                            <c:forEach var="notification" items="${unreadNotifications}">
                                <div class="list-group-item list-group-item-action" data-notification-id="${notification.getId()}">
                                    <div class="d-flex w-100 justify-content-between">
                                        <h5 class="mb-1">Notification</h5>
                                        <small>
                                         <p class="mb-1">${notification.getTimestamp().toString().substring(0, 16).replace('T', ' ')}</p>
                                        </small>
                                    </div>
                                    <p class="mb-1">${notification.getMessage()}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info" role="alert">
                            No unread notifications
                        </div>
                    </c:otherwise>
                </c:choose>

                <hr>

                <h5 class="mt-4">Previous Notifications</h5>
                <c:choose>
                    <c:when test="${not empty readNotifications}">
                        <div class="list-group">
                            <c:forEach var="notification" items="${readNotifications}">
                                <div class="list-group-item text-muted">
                                    <div class="d-flex w-100 justify-content-between">
                                        <h5 class="mb-1">Notification</h5>
                                        <small>


                                            <p class="mb-1">${notification.getTimestamp().toString().substring(0, 16).replace('T', ' ')}</p>

                                        </small>
                                    </div>
                                    <p class="mb-1">${notification.getMessage()}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info" role="alert">
                            No previous notifications
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const markAllReadBtn = document.getElementById('markAllRead');

            markAllReadBtn.addEventListener('click', function() {
                // AJAX call to mark all notifications as read
                fetch('/CourseRequest/notifications', {
                    method: 'POST'
                })
                .then(response => {
                 if (response.ok) {
                    // Reload to fetch updated notifications
                    location.reload();
                } else {
                    console.error('Failed to mark notifications as read.');
                }

                });
            });
        });
    </script>
</body>
</html>