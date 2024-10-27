package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.Event;
import mk.ukim.finki.mk.lab.repository.EventRepository;

import mk.ukim.finki.mk.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;       // Without @Repository in "EventRepository" will throw error because of no autowire.
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String eventName, double minRating) {
        return eventRepository.findAll().stream()
                .filter(event -> event.getName().toLowerCase().contains(eventName.toLowerCase()) &&
                        event.getPopularityScore() >= minRating)
                .collect(Collectors.toList());
    }
}