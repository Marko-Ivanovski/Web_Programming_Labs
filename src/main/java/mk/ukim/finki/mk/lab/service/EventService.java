package mk.ukim.finki.mk.lab.service;

import mk.ukim.finki.mk.lab.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> listAll();

    List<Event> searchEvents(String text, double min);

    Optional<Event> findById(Long id);

    Optional<Event> findByName(String name);

    Optional<Event> save(String name, String description, double popularityScore, Long locationId);

    void deleteById(Long id);
}