package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Event;
import service.EventService;

import java.io.IOException;
import java.util.List;

@WebServlet("/events/filter")
public class EventFilterServlet extends HttpServlet {
    private EventService eventService;

    @Override
    public void init() throws ServletException {
        this.eventService = (EventService) getServletContext().getAttribute("eventService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventTypeIdParam = request.getParameter("eventTypeId");

        List<Event> filteredEvents;

        try {
            if (eventTypeIdParam != null && !eventTypeIdParam.isEmpty()) {
                Long eventTypeId = Long.parseLong(eventTypeIdParam);
                filteredEvents = eventService.getEventsByType(eventTypeId);
            } else {
                filteredEvents = eventService.getAllEvents();
            }

            request.setAttribute("events", filteredEvents);
            request.setAttribute("eventTypes", eventService.getAllEventTypes());
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("pageTitle", "Мероприятия");
            request.getRequestDispatcher("/events-list.ftlh").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка фильтрации: " + e.getMessage());
            request.setAttribute("events", eventService.getAllEvents());
            request.setAttribute("eventTypes", eventService.getAllEventTypes());
            request.setAttribute("contextPath", request.getContextPath());
            request.getRequestDispatcher("/events-list.ftlh").forward(request, response);
        }
    }
}
