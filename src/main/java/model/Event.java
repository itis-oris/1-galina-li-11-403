package model;

import java.time.LocalDateTime;

public class Event {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String location;
    private Long eventTypeId;
    private EventType eventType;

    public Event() {
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Event(EventType eventType, Long eventTypeId) {
        this.eventType = eventType;
        this.eventTypeId = eventTypeId;
    }

    public Event(Long id, Long userId, String title, String description, LocalDateTime eventDate, String location) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}