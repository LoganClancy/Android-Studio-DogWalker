package com.example.cosc341_step4;

import java.util.ArrayList;
import java.util.List;

public class NotificationDB {
    private static final List<NotificationEntry> notificationStorage = new ArrayList<>();

    public static void add(NotificationEntry notif){
        notificationStorage.add(notif);
    }

    public static List<NotificationEntry> getAll(){
        return notificationStorage;
    }
}
