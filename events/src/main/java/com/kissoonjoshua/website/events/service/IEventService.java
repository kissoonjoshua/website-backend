package com.kissoonjoshua.website.events.service;

import com.kissoonjoshua.website.events.model.Event;

import java.util.List;

public interface IEventService {
    public Event saveEvent(Event event);
    public List<Event> fetchEvents();
    public Event fetchEventByVideoId(String videoId);
    public void deleteEventById(String eventId);
}
