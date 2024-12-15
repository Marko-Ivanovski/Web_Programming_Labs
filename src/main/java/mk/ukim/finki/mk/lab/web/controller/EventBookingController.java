package mk.ukim.finki.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.mk.lab.model.Event;
import mk.ukim.finki.mk.lab.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class EventBookingController {
    private final EventService eventService;

    public EventBookingController(EventService eventService) {
        this.eventService = eventService;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp != null && !clientIp.isEmpty()) {
            return clientIp.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    // POST Event booked
    @PostMapping("/eventBooking")
    public String bookEvent(@RequestParam Long eventId,
                            @RequestParam int numTickets,
                            HttpServletRequest request,
                            Model model) {
        String clientIp = getClientIpAddress(request);

        Optional<Event> event = eventService.findById(eventId);

        if (event.isPresent()) {
            // Add attributes to model if needed
            model.addAttribute("eventName", event.get().getName());
            model.addAttribute("numTickets", numTickets);
            model.addAttribute("attendeeName", "Petko Petkov");
            model.addAttribute("attendeeAddress", clientIp);
            return "redirect:/bookingConfirmation";
        } else {
            model.addAttribute("error", "Event not found for ID: " + eventId);
            return "listEvents";
        }
    }

    // GET for the booking confirmation page
    @GetMapping("/bookingConfirmation")
    public String showBookingConfirmation(HttpSession session, Model model) {
        String eventName = (String) session.getAttribute("eventName");
        Integer numTickets = (Integer) session.getAttribute("numTickets");
        String attendeeName = (String) session.getAttribute("attendeeName");
        String attendeeAddress = (String) session.getAttribute("attendeeAddress");

        if (eventName == null || numTickets == null || attendeeName == null || attendeeAddress == null) {
            model.addAttribute("error", "Booking information not found.");
            return "listEvents";  // Redirect to another page if session is empty
        }

        model.addAttribute("eventName", eventName);
        model.addAttribute("numTickets", numTickets);
        model.addAttribute("attendeeName", attendeeName);
        model.addAttribute("attendeeAddress", attendeeAddress);

        return "bookingConfirmation";  // This should correspond to bookingConfirmation.html
    }
}
