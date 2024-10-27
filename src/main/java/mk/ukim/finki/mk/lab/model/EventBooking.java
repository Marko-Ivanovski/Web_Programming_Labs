package mk.ukim.finki.mk.lab.model;

import lombok.Getter;
import lombok.Setter;

public class EventBooking {
    @Getter @Setter
    private String eventName;

    @Getter @Setter
    private String attendeeName;

    @Getter @Setter
    private String attendeeAddress;

    @Getter @Setter
    private Long numberOfTickets;

    public EventBooking(String eventName, String attendeeName, String attendeeAddress, Long numberOfTickets) {
        this.eventName = eventName;
        this.attendeeName = attendeeName;
        this.attendeeAddress = attendeeAddress;
        this.numberOfTickets = numberOfTickets;
    }
}

