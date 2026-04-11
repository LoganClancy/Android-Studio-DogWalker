package com.example.cosc341_step4.notifications;

import com.example.cosc341_step4.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationDB {
    private static final List<NotificationEntry> notificationStorage = new ArrayList<>();

    public static void add(NotificationEntry notif){
        notificationStorage.add(notif);
    }

    public static void addMapNotif(String title, String location){
        NotificationEntry notif = new NotificationEntry("New map marker added!", "Name: " + title + "\n" + location, R.drawable.map_pin);
        notificationStorage.add(notif);
    }

    public static void addEventCreatedNotif(String name, String location){
        NotificationEntry notif = new NotificationEntry("New event created!", "Name: " + name + "\n" + location, R.drawable.event_creation);
        notificationStorage.add(notif);
    }

    public static void addChatMessageNotif(String sender, String message){
        NotificationEntry notif = new NotificationEntry("New message from " + sender, message, R.drawable.new_message);
        notificationStorage.add(notif);
    }

    public static void addWalkCreatedNotif(String name, String location){
        NotificationEntry notif = new NotificationEntry("New walk created!", "Name: " + name + "\n" + location, R.drawable.event_creation);
        notificationStorage.add(notif);
    }

    public static List<NotificationEntry> getAll(){
        return notificationStorage;
    }
}
