package mk.ukim.finki.mk.lab.repository;

import mk.ukim.finki.mk.lab.model.Event;
import mk.ukim.finki.mk.lab.bootstrap.DataHolder;
import mk.ukim.finki.mk.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventRepository {
    public List<Event> findAll() {
        return DataHolder.events;
    }

    public List<Event> searchNames(String text, Double min) {
        return DataHolder.events.stream()
                .filter(r -> (r.getName().contains(text) || r.getDescription().contains(text)) &&
                        (r.getPopularityScore() >= min))
                .collect(Collectors.toList());
    }

    public Optional<Event> findById(Long id) {
        return DataHolder.events.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    public Optional<Event> findByName(String name) {
        return DataHolder.events.stream()
                .filter(r -> r.getName().equals(name))
                .findFirst();
    }

    public Optional<Event> save(String name, String description, double popularityScore, Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        Event event = new Event(name, description, popularityScore, location);
        DataHolder.events.removeIf(r -> r.getName().equals(name));
        DataHolder.events.add(event);
        return Optional.of(event);
    }

    public void deleteById(Long id) {
        DataHolder.events.removeIf(r -> r.getId().equals(id));
    }
}
