package com.example.cosc341_step4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationEntry {
    String title, message, time;
    int iconID;
//    Boolean tooLong;

    public NotificationEntry(String title, String message, int iconID){
        this.title = title;
        this.message = message;
        this.iconID = iconID;

        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        this.time = timeFormat.format(new Date());
    }
}
