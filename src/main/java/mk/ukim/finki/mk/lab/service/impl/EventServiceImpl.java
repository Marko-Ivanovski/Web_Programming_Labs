package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.Event;
import mk.ukim.finki.mk.lab.model.Location;
import mk.ukim.finki.mk.lab.repository.EventRepository;
import mk.ukim.finki.mk.lab.service.EventService;
import mk.ukim.finki.mk.lab.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationService locationService;

    public EventServiceImpl(EventRepository eventRepository, LocationService locationService) {
        this.eventRepository = eventRepository;
        this.locationService = locationService;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public void save(Long id, String name, String description, double popularityScore, Long locationId) {
        Location location = locationService.findById(locationId);
        if (location == null) {
            throw new IllegalArgumentException("Location with ID " + locationId + " not found");
        }

        Event event;

        if (id != null) {
            event = eventRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Event with ID " + id + " not found"));
        } else {
            event = new Event();
        }

        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        event.setLocation(location);

        eventRepository.save(event);
    }


    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> findByLocationId(Long locationId) {
        return eventRepository.findAllByLocation_Id(locationId);
    }

    @Override
    public Optional<Event> findByName(String name) {
        return eventRepository.findByName(name);
    }

    @Override
    public List<Event> searchEvents(String text, double min) {
        return eventRepository.findAll().stream()
                .filter(event -> event.getName().contains(text) || event.getDescription().contains(text))
                .filter(event -> event.getPopularityScore() >= min)
                .toList();
    }
}