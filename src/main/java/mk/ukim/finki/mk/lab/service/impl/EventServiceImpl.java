package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.Event;
import mk.ukim.finki.mk.lab.model.Location;
import mk.ukim.finki.mk.lab.repository.EventRepository;

import mk.ukim.finki.mk.lab.repository.LocationRepository;
import mk.ukim.finki.mk.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public EventServiceImpl(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text, double min) {
        return eventRepository.searchNames(text, min);
    }

    @Override
    public Optional<Event> findById(Long id){
        return eventRepository.findById(id);
    }

    @Override
    public Optional<Event> findByName(String name) {
        return eventRepository.findByName(name);
    }

    @Override
    public Optional<Event> save(String name, String description, double popularityScore, Long locationId) {
        Location location = this.locationRepository.findById(locationId).orElse(null);
        return this.eventRepository.save(name, description, popularityScore, location);
    }

    @Override
    public void deleteById(Long id) {
        this.eventRepository.deleteById(id);
    }
}