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

    // GET
    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Event> events = this.eventService.listAll();
        model.addAttribute("events", events);
        return "listEvents";
    }

    // GET Add
    @GetMapping("/add-form")
    public String getAddEventForm(Model model){
        List<Location> locations = this.locationService.findAll();
        model.addAttribute("locations", locations);
        return "add-event";
    }

    // POST Add
    @PostMapping("/add")
    public String saveEvent(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId) {
        this.eventService.save(name, description, popularityScore, locationId);
        return "redirect:/events";
    }

    // GET Edit
    @GetMapping("/edit-form/{id}")
    public String editEventForm(@PathVariable Long id, Model model){
        if(this.eventService.findById(id).isPresent()){
            Event event = this.eventService.findById(id).get();
            List<Location> locations = this.locationService.findAll();
            model.addAttribute("locations", locations);
            model.addAttribute("event", event);
            return "add-event";
        }
        return "redirect:/events?error=invalid-event-or-location";
    }

    // POST Edit
    @PostMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId) {
        Optional<Event> optionalEvent = this.eventService.findById(id);
        Optional<Location> optionalLocation = this.locationService.findById(locationId);

        if (optionalEvent.isPresent() && optionalLocation.isPresent()) {
            Event event = optionalEvent.get();
            Location location = optionalLocation.get();

            event.setName(name);
            event.setDescription(description);
            event.setPopularityScore(popularityScore);
            event.setLocation(location);

            this.eventService.save(event.getName(), event.getDescription(), event.getPopularityScore(), location.getId());
            return "redirect:/events";
        }
        return "redirect:/events?error=invalid-event-or-location";
    }

    // POST Delete
    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id){
        this.eventService.deleteById(id);
        return "redirect:/events";
    }
}