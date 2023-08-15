package com.kissoonjoshua.website.events.repository;

import com.kissoonjoshua.website.events.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
}
