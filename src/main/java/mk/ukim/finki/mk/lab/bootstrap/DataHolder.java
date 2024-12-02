package mk.ukim.finki.mk.lab.bootstrap;

import mk.ukim.finki.mk.lab.model.*;
import mk.ukim.finki.mk.lab.repository.EventRepository;
import mk.ukim.finki.mk.lab.repository.LocationRepository;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Location> locations = null;
    public static List<Event> events = null;

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    public DataHolder(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @PostConstruct
    public void init() {
        // Locations
        locations = new ArrayList<>();
        if (this.locationRepository.count() == 0) {
            locations.add(new Location("Conference Hall A", "123 Main St", "200", "Main conference hall for large events"));
            locations.add(new Location("Outdoor Park", "45 Park Ave", "500", "Outdoor area for large gatherings"));
            locations.add(new Location("Exhibition Room", "98 Expo Blvd", "150", "Room for exhibitions and displays"));
            locations.add(new Location("Workshop Center", "210 Learning Way", "100", "Center for hands-on workshops"));
            locations.add(new Location("Auditorium", "12 Theater Dr", "300", "Auditorium for presentations and performances"));
            this.locationRepository.saveAll(locations);
        }

        // Events
        events = new ArrayList<>();
        if (this.eventRepository.count() == 0) {
            events.add(new Event("Tech Conference","Description-1",4.5, locations.get(0)));
            events.add(new Event("AI Summit","Description-2",5.3, locations.get(0)));
            events.add(new Event("Science Fair","Description-3",1.9, locations.get(2)));
            events.add(new Event("Art Showcase","Description-4",2.2, locations.get(2)));
            events.add(new Event("Workshop","Description-5",7.1, locations.get(3)));
            events.add(new Event("Film Festival","Description-6",9.0, locations.get(4)));
            events.add(new Event("Small Film Screening","Description-7",7.7, locations.get(1)));
            events.add(new Event("Music Festival","Description-8",3.6, locations.get(4)));
            events.add(new Event("Opera","Description-9",1.2, locations.get(0)));
            events.add(new Event("Car Exhibit","Description-10",5.6, locations.get(1)));
            this.eventRepository.saveAll(events);
        }
    }
}