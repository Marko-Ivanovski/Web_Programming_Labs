package mk.ukim.finki.mk.lab.web.controller;

import mk.ukim.finki.mk.lab.model.Event;
import mk.ukim.finki.mk.lab.model.Location;
import mk.ukim.finki.mk.lab.service.EventService;
import mk.ukim.finki.mk.lab.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final LocationService locationService;

    public EventController(EventService eventService, LocationService locationService) {
        this.eventService = eventService;
        this.locationService = locationService;
    }

    // GET: List Events
    @GetMapping
    public String getEventsPage(@RequestParam(required = false) Long locationId, Model model) {
        List<Location> locations = this.locationService.findAll();

        List<Event> events;
        if (locationId != null && locationId > 0) {
            events = this.eventService.findByLocationId(locationId);
        } else {
            events = this.eventService.listAll();
        }

        model.addAttribute("locations", locations);
        model.addAttribute("events", events);

        return "listEvents";
    }


    // GET: Add Event Form
    @GetMapping("/add-form")
    public String getAddEventForm(Model model) {
        List<Location> locations = this.locationService.findAll();
        model.addAttribute("locations", locations);
        return "add-event";
    }

    // POST: Add Event
    @PostMapping("/add")
    public String saveEvent(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId) {
        try {
            this.eventService.save(null, name, description, popularityScore, locationId);
            return "redirect:/events";
        } catch (IllegalArgumentException e) {
            return "redirect:/events/add-form?error=" + e.getMessage();
        }
    }

    // GET: Edit Event Form
    @GetMapping("/edit-form/{id}")
    public String getEditEventForm(@PathVariable Long id, Model model) {
        Optional<Event> optionalEvent = this.eventService.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            List<Location> locations = this.locationService.findAll();
            model.addAttribute("locations", locations);
            model.addAttribute("event", event);
            return "add-event";
        }
        return "redirect:/events?error=Event not found";
    }

    // POST: Edit Event
    @PostMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId) {
        try {
            Optional<Event> optionalEvent = this.eventService.findById(id);
            Location location = this.locationService.findById(locationId);

            if (optionalEvent.isPresent() && location != null) {
                Event event = optionalEvent.get();
                event.setName(name);
                event.setDescription(description);
                event.setPopularityScore(popularityScore);
                event.setLocation(location);

                this.eventService.save(event.getId(), event.getName(), event.getDescription(), event.getPopularityScore(), location.getId());
                return "redirect:/events";
            }
            return "redirect:/events?error=Invalid event or location";
        } catch (IllegalArgumentException e) {
            return "redirect:/events?error=" + e.getMessage();
        }
    }

    // POST: Delete Event
    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        this.eventService.deleteById(id);
        return "redirect:/events";
    }
}
