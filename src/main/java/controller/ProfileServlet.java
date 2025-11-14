package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.EventService;
import service.CarService;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private EventService eventService;
    private CarService carService;

    @Override
    public void init() {
        this.eventService = (EventService) getServletContext().getAttribute("eventService");
        this.carService = new CarService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        request.setAttribute("userEvents", eventService.getUserEvents(currentUser));
        request.setAttribute("registeredEvents", eventService.getUserRegisteredEvents(currentUser.getId()));
        request.setAttribute("userCars", carService.getUserCars(currentUser.getId()));

        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("pageTitle", "Мой профиль");
        request.getRequestDispatcher("/profile.ftlh").forward(request, response);
    }
}