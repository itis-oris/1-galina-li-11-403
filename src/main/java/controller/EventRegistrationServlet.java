package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.EventRegistrationService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/events/registration/*")
public class EventRegistrationServlet extends HttpServlet {

    private EventRegistrationService registrationService;

    @Override
    public void init() throws ServletException {
        this.registrationService = new EventRegistrationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/events");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        String eventIdParam = req.getParameter("eventId");

        System.out.println("PathInfo: " + pathInfo);
        System.out.println("EventIdParam: " + eventIdParam);
        System.out.println("User: " + (currentUser != null ? currentUser.getUsername() : "null"));

        if(eventIdParam == null) {
            resp.sendError(400, "ID мероприятия обязателен");
            return;
        }

        try {
            Long eventId = Long.parseLong(eventIdParam);

            if (pathInfo != null && pathInfo.equals("/cancel")) {
                registrationService.cancelRegistration(currentUser.getId(), eventId);
                resp.sendRedirect(req.getContextPath() + "/events/" + eventId);
            } else {
                registrationService.registerForEvent(currentUser.getId(), eventId);
                resp.sendRedirect(req.getContextPath() + "/events/" + eventId);
            }
        } catch (NumberFormatException e) {
            resp.sendError(400, "Неверный формат ID мероприятия");
        } catch (IllegalArgumentException e) {
            session.setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/events/" + eventIdParam);
        } catch (Exception e) {
            resp.sendError(500, "Ошибка сервера: " + e.getMessage());
        }
    }
}