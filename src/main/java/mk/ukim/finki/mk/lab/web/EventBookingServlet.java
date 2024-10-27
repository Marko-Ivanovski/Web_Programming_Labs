package mk.ukim.finki.mk.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.mk.lab.service.impl.EventServiceImpl;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "eventBookingServlet", urlPatterns = "/eventBooking")
public class EventBookingServlet extends HttpServlet {

    private final EventServiceImpl eventService;
    private final SpringTemplateEngine springTemplateEngine;

    public EventBookingServlet(EventServiceImpl eventService, SpringTemplateEngine springTemplateEngine){
        this.eventService = eventService;
        this.springTemplateEngine = springTemplateEngine;
    }

    // EventBookingServlet
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventName = req.getParameter("eventName");
        String numTickets = req.getParameter("numTickets");

        IWebExchange iWebExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req,resp);
        WebContext context = new WebContext(iWebExchange);

        context.setVariable("eventName",eventName);
        context.setVariable("numTickets",numTickets);
        context.setVariable("attendeeName", "Petko Petkov");
        context.setVariable("attendeeAddress", req.getRemoteAddr());

        springTemplateEngine.process("bookingConfirmation.html",context,resp.getWriter());
    }

}

