package mk.ukim.finki.mk.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.mk.lab.model.Event;
import mk.ukim.finki.mk.lab.service.EventService;
import mk.ukim.finki.mk.lab.service.impl.EventServiceImpl;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "eventListServlet", urlPatterns = "/event")
public class EventListServlet extends HttpServlet {

    private final EventService eventService;
    private final SpringTemplateEngine springTemplateEngine;

    public EventListServlet(EventServiceImpl eventService, SpringTemplateEngine springTemplateEngine){
        this.eventService = eventService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange iWebExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        String searchText = req.getParameter("searchText");
        String min = req.getParameter("min");
        Double _minRating = (min != null && !min.isEmpty()) ? Double.valueOf(min) : null;

        List<Event> eventList;
        if ((searchText != null && !searchText.isEmpty()) || _minRating != null) {
            eventList = eventService.searchEvents(searchText, 0);
        } else {
            eventList = eventService.listAll();
        }
        WebContext context = new WebContext(iWebExchange);
        context.setVariable("events", eventList);
        springTemplateEngine.process("listEvents.html", context, resp.getWriter());
    }
}
