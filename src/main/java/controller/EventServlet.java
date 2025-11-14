package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Event;
import model.User;
import service.EventService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@WebServlet("/events/*")
public class EventServlet extends HttpServlet {
    private EventService eventService;

    @Override
    public void init() {
        this.eventService = (EventService) getServletContext().getAttribute("eventService");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        setupCommonAttributes(req);

        if (pathInfo == null || pathInfo.equals("/")) {
            handleAllEvents(req, resp);
        } else if (pathInfo.equals("/my")) {
            handleMyEvents(req, resp, currentUser);
        } else if (pathInfo.equals("/new")) {
            handleNewEventForm(req, resp, currentUser);
        } else if (pathInfo.startsWith("/edit/")) {
            handleEditEventForm(req, resp, currentUser, pathInfo);
        } else if (pathInfo.startsWith("/")) {
            handleEventDetails(req, resp, currentUser, pathInfo);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        setupCommonAttributes(req);

        if (pathInfo == null || pathInfo.equals("/")) {
            createEvent(req, resp, currentUser);
        } else if (pathInfo.equals("/update")) {
            updateEvent(req, resp, currentUser);
        } else if (pathInfo.equals("/delete")) {
            deleteEvent(req, resp, currentUser);
        }
    }

    private void setupCommonAttributes(HttpServletRequest request) {
        request.setAttribute("contextPath", request.getContextPath());
    }

    private void setupEventFormAttributes(HttpServletRequest request) {
        request.setAttribute("eventTypes", eventService.getAllEventTypes());
    }


    private void handleAllEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("events", eventService.getAllEvents());
        request.setAttribute("eventTypes", eventService.getAllEventTypes());
        request.setAttribute("pageTitle", "Все мероприятия");
        request.getRequestDispatcher("/events-list.ftlh").forward(request, response);
    }

    private void handleMyEvents(HttpServletRequest request, HttpServletResponse response, User currentUser) throws IOException, ServletException {
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        request.setAttribute("events", eventService.getUserEvents(currentUser));
        request.setAttribute("eventTypes", eventService.getAllEventTypes());
        request.setAttribute("pageTitle", "Мои мероприятия");
        request.getRequestDispatcher("/events-list.ftlh").forward(request, response);
    }

    private void handleNewEventForm(HttpServletRequest request, HttpServletResponse response, User currentUser) throws IOException, ServletException {
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        setupEventFormAttributes(request);
        request.setAttribute("pageTitle", "Создание мероприятия");
        request.getRequestDispatcher("/event-form.ftlh").forward(request, response);
    }

    private void handleEditEventForm(HttpServletRequest request, HttpServletResponse response, User currentUser, String pathInfo) throws IOException, ServletException {
        try {
            Long eventId = Long.parseLong(pathInfo.substring(6));
            Optional<Event> eventOpt = eventService.getEventById(eventId);

            if (eventOpt.isPresent()) {
                Event event = eventOpt.get();

                if (!eventService.isUserOrganizer(event, currentUser)) {
                    response.sendError(403, "Вы не можете редактировать это мероприятие");
                    return;
                }
                setupEventFormAttributes(request);
                request.setAttribute("event", event);
                request.setAttribute("pageTitle", "Редактирование мероприятия");
                request.getRequestDispatcher("/event-form.ftlh").forward(request, response);
            } else {
                response.sendError(404, "Мероприятие не найдено");
            }
        } catch (NumberFormatException e) {
            response.sendError(400, "Неверный ID мероприятия");
        }
    }

    private void handleEventDetails(HttpServletRequest request, HttpServletResponse response, User currentUser, String pathInfo) throws IOException, ServletException {
        try {
            Long eventId = Long.parseLong(pathInfo.substring(1));
            Optional<Event> eventOpt = eventService.getEventById(eventId);

            if (eventOpt.isPresent()) {
                Event event = eventOpt.get();
                request.setAttribute("event", event);
                request.setAttribute("pageTitle", event.getTitle());

                if (currentUser != null) {
                    request.setAttribute("isOrganizer", eventService.isUserOrganizer(event, currentUser));
                    request.setAttribute("isRegistered", eventService.isRegistered(currentUser.getId(), eventId));
                }

                request.setAttribute("participantsCount", eventService.getRegisteredUsersCount(eventId));
                request.setAttribute("eventTypes", eventService.getAllEventTypes());
                request.getRequestDispatcher("/event-details.ftlh").forward(request, response);
            } else {
                response.sendError(404, "Мероприятие не найдено");
            }
        } catch (NumberFormatException e) {
            response.sendError(400, "Неверный ID мероприятия");
        }
    }

    private Event createEventFromRequest(HttpServletRequest request) {
        Event event = new Event();

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String eventTypeIdStr = request.getParameter("eventTypeId");
        String eventDateStr = request.getParameter("eventDate");

        if (eventDateStr == null) {
            String datePart = request.getParameter("eventDateDate");
            String timePart = request.getParameter("eventDateTime");
            if (datePart != null && timePart != null) {
                eventDateStr = datePart + "T" + timePart;
            }
        }


        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Название мероприятия обязательно");
        }

        if (eventTypeIdStr == null) {
            throw new IllegalArgumentException("Тип мероприятия обязателен");
        }

        if (eventDateStr == null) {
            throw new IllegalArgumentException("Дата мероприятия обязательна");
        }

        event.setTitle(title.trim());
        event.setDescription(description != null ? description.trim() : "");
        event.setLocation(location != null ? location.trim() : "");
        event.setEventTypeId(Long.parseLong(eventTypeIdStr));

        try {
            LocalDateTime eventDate = LocalDateTime.parse(eventDateStr, DateTimeFormatter.ISO_DATE_TIME);
            event.setEventDate(eventDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неверный формат даты");
        }
        return event;
    }

    private void updateEventFromData(Event target, Event source) {
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setLocation(source.getLocation());
        target.setEventTypeId(source.getEventTypeId());
        target.setEventDate(source.getEventDate());
    }

    private void createEvent(HttpServletRequest request, HttpServletResponse response, User currentUser) throws IOException, ServletException {
        try {
            Event event = createEventFromRequest(request);

            Event createdEvent = eventService.createEvent(event, currentUser);
            response.sendRedirect(request.getContextPath() + "/events/" + createdEvent.getId());
        } catch (Exception e) {
            setupEventFormAttributes(request);
            request.setAttribute("errorMessage", "Ошибка при создании мероприятия: " + e.getMessage());
            request.setAttribute("pageTitle", "Создание мероприятия");
            request.getRequestDispatcher("/event-form.ftlh").forward(request, response);
        }
    }

    private void updateEvent(HttpServletRequest request, HttpServletResponse response, User currentUSer) throws IOException, ServletException {
        try {
            Long eventId = Long.parseLong(request.getParameter("id"));
            Optional<Event> eventOpt = eventService.getEventById(eventId);

            if (eventOpt.isPresent()) {
                Event event = eventOpt.get();

                Event updateData = createEventFromRequest(request);
                updateEventFromData(event, updateData);

                eventService.updateEvent(event, currentUSer);
                response.sendRedirect(request.getContextPath() + "/events/" + event.getId());
            } else {
                response.sendError(404, "Мероприятие не найдено");
            }
        } catch (Exception e) {
            setupEventFormAttributes(request);
            request.setAttribute("errorMessage", "Ошибка при обновлении мероприятия: " + e.getMessage());
            request.setAttribute("pageTitle", "Редактирование мероприятия");
            request.getRequestDispatcher("/event-form.ftlh").forward(request, response);
        }
    }

    private void deleteEvent(HttpServletRequest request, HttpServletResponse response, User currentUser) throws IOException {
        try {
            Long eventId = Long.parseLong(request.getParameter("id"));
            eventService.deleteEvent(eventId, currentUser);
            response.sendRedirect(request.getContextPath() + "/events/my");
        } catch (Exception e) {
            response.sendError(400, "Ошибка при удалении мероприятия: " + e.getMessage());
        }
    }
}