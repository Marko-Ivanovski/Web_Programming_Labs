package mk.ukim.finki.mk.lab.repository;

import mk.ukim.finki.mk.lab.model.Event;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;

@Repository
public class EventRepository {
    List<Event> events = new ArrayList<>();

    public EventRepository() {
        events.add(new Event("Event-1","Description-1",4.5));
        events.add(new Event("Event-2","Description-2",5.3));
        events.add(new Event("Event-3","Description-3",1.9));
        events.add(new Event("Event-4","Description-4",2.2));
        events.add(new Event("Event-5","Description-5",7.1));
        events.add(new Event("Event-6","Description-6",9.0));
        events.add(new Event("Event-7","Description-7",7.7));
        events.add(new Event("Event-8","Description-8",3.6));
        events.add(new Event("Event-9","Description-9",1.2));
        events.add(new Event("Event-10","Description-10",5.6));
    }

    public List<Event> findAll() {
        return events;
    }

    public List<Event> searchEvents(String text) {
        return events.stream()
                .filter(e -> e.getName().contains(text) || e.getDescription().contains(text))
                .toList();
    }
}
