package com.kissoonjoshua.website.events.service;

import com.kissoonjoshua.website.events.model.Event;
import com.kissoonjoshua.website.events.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService implements IEventService {
    private EventRepository eventRepository;

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> fetchEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event fetchEventByVideoId(String videoId) {
        Optional<Event> event = eventRepository.findById(videoId);
        return event.orElse(null);
    }

    @Override
    public void deleteEventById(String eventId) {
        eventRepository.deleteById(eventId);
    }
}
