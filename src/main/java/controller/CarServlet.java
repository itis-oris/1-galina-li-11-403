package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Car;
import model.User;
import service.CarService;

import java.io.IOException;

@WebServlet("/cars/*")
public class CarServlet extends HttpServlet {
    private CarService carService;

    @Override
    public void init() throws ServletException {
        this.carService = new CarService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = getAuthenticatedUser(request, response);
        if (currentUser == null) return;

        String pathInfo = request.getPathInfo();
        request.setAttribute("contextPath", request.getContextPath());

        if (pathInfo == null || pathInfo.equals("/")) {
            request.setAttribute("cars", carService.getUserCars(currentUser.getId()));
            request.setAttribute("pageTitle", "Мои автомобили");
            request.getRequestDispatcher("/cars-list.ftlh").forward(request, response);
        } else if (pathInfo.equals("/new")) {
            request.setAttribute("pageTitle", "Добавить автомобиль");
            request.getRequestDispatcher("/car-form.ftlh").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = getAuthenticatedUser(request, response);
        if (currentUser == null) return;

        String pathInfo = request.getPathInfo();
        request.setAttribute("contextPath", request.getContextPath());

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                Car car = new Car();
                car.setBrand(request.getParameter("brand"));
                car.setModel(request.getParameter("model"));
                car.setYear(Integer.parseInt(request.getParameter("year")));

                carService.addCar(car, currentUser.getId());
                response.sendRedirect(request.getContextPath() + "/cars");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            request.setAttribute("pageTitle", "Добавить автомобиль");
            request.getRequestDispatcher("/car-form.ftlh").forward(request, response);
        }
    }

    private User getAuthenticatedUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return null;
        }
        return currentUser;
    }
}
