package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/auth/check")
public class UserCheckServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> userOptional = userService.authenticate(username, password);

        if (userOptional.isPresent()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", userOptional.get());

            response.sendRedirect(request.getContextPath() + "/index");
        } else {
            request.setAttribute("errorMessage", "Неверное имя пользователя или пароль!");
            request.setAttribute("contextPath", request.getContextPath());
            request.getRequestDispatcher("/login.ftlh").forward(request, response);
        }
    }
}