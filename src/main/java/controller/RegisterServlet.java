package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserService;

import java.io.IOException;

@WebServlet("/auth/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("contextPath", request.getContextPath());
        request.getRequestDispatcher("/register.ftlh")
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            boolean isRegistered = userService.registerUser(user);

            if (isRegistered) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
            } else {
                request.setAttribute("errorMessage", "Пользователь с таким именем или email уже существует!");
                request.setAttribute("contextPath", request.getContextPath());
                request.getRequestDispatcher("/register.ftlh").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при регистрации: " + e.getMessage());
            request.setAttribute("contextPath", request.getContextPath());
            request.getRequestDispatcher("/register.ftlh").forward(request, response);
        }
    }
}