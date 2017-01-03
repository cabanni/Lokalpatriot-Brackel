package com.example.cabanni.lokalpatriot_brackel;

public class Event {

    private String eventName;
    private String timestamp;

    public Event(String eventName, String timestamp) {
        super();
        this.eventName = eventName;
        this.timestamp = timestamp;
    }

    public Event() {
        // TODO Auto-generated constructor stub
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}