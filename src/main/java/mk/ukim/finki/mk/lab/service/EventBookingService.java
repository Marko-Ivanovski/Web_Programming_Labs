package mk.ukim.finki.mk.lab.service;

import mk.ukim.finki.mk.lab.model.EventBooking;

public interface EventBookingService {
    EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, int numberOfTickets);
}
