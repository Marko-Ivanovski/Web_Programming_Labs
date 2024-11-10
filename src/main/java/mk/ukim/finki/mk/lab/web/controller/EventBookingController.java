package mk.ukim.finki.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.mk.lab.model.Event;
import mk.ukim.finki.mk.lab.service.EventService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @PostMapping("/event-booking")
    public String bookEvent(@RequestParam Long eventId,
                            @RequestParam int numTickets,
                            HttpServletRequest request,
                            Model model) {
        String clientIp = getClientIpAddress(request);

        Optional<Event> event = eventService.findById(eventId);

        event.ifPresentOrElse(e -> {
            model.addAttribute("eventName", e.getName());
            model.addAttribute("numTickets", numTickets);
            model.addAttribute("attendeeName", "Petko Petkov");
            model.addAttribute("attendeeAddress", clientIp);
        }, () -> model.addAttribute("error", "Event not found for ID: " + eventId));

        return "bookingConfirmation";
    }

    // GET for the booking confirmation page, where we retrieve data from the session
    @PostMapping("/bookingConfirmation")
    public String showBookingConfirmation(HttpSession session, Model model) {
        String eventName = (String) session.getAttribute("eventName");
        Integer numTickets = (Integer) session.getAttribute("numTickets");
        String attendeeName = (String) session.getAttribute("attendeeName");
        String attendeeAddress = (String) session.getAttribute("attendeeAddress");

        model.addAttribute("eventName", eventName);
        model.addAttribute("numTickets", numTickets);
        model.addAttribute("attendeeName", attendeeName);
        model.addAttribute("attendeeAddress", attendeeAddress);

        return "bookingConfirmation";
    }
}