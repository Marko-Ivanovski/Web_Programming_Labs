package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.EventBooking;
import mk.ukim.finki.mk.lab.service.EventBookingService;
import org.springframework.stereotype.Service;

@Service
public class EventBookingServiceImpl implements EventBookingService {
    @Override
    public EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, int numberOfTickets){
        return new EventBooking(eventName, attendeeName, attendeeAddress, (long) numberOfTickets);
    }
}