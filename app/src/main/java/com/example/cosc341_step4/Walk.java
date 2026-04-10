// models/Walk.java
package com.example.cosc341_step4;

public class Walk {
    private int id;
    private String title;
    private String description;
    private String time;
    private String startLocation;
    private String endLocation;
    private String participants;
    private boolean isJoined;

    public Walk(int id, String title, String description, String time,
                String startLocation, String endLocation, String participants,
                boolean isJoined) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.participants = participants;
        this.isJoined = isJoined;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }

    public String getEndLocation() { return endLocation; }
    public void setEndLocation(String endLocation) { this.endLocation = endLocation; }

    public String getParticipants() { return participants; }
    public void setParticipants(String participants) { this.participants = participants; }

    public boolean isJoined() { return isJoined; }
    public void setJoined(boolean joined) { isJoined = joined; }
}