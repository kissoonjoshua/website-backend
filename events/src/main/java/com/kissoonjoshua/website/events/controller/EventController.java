package com.kissoonjoshua.website.events.controller;

import com.kissoonjoshua.website.events.model.Event;
import com.kissoonjoshua.website.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("")
    public List<Event> fetchEvents() {
        return eventService.fetchEvents();
    }

    @GetMapping("/video/{id}")
    public Event fetchEventByVideoId(@PathVariable("id") String videoId) {
        return eventService.fetchEventByVideoId(videoId);
    }

    @PostMapping("")
    public Event saveEvent(@RequestBody Event event) {
        return eventService.saveEvent(event);
    }

    @DeleteMapping("/{id}")
    public String deleteEventById(@PathVariable("id") String id) {
        eventService.deleteEventById(id);
        return "Event deleted";
    }
}
