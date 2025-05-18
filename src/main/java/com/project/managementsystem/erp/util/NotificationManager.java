package com.project.managementsystem.erp.util;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private static NotificationManager instance;
    private final List<String> notifications;

    private NotificationManager() {
        notifications = new ArrayList<>();
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public void addNotification(String message) {
        notifications.add(message);
    }

    public List<String> getNotifications() {
        return notifications;
    }
}
