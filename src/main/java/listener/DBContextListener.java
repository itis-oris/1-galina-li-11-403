package listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import repository.DbConnection;
import service.EventRegistrationService;
import service.EventService;
import service.UserService;

@WebListener
public class DBContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DbConnection.init();

            ServletContext context = sce.getServletContext();
            context.setAttribute("userService", new UserService());
            context.setAttribute("eventService", new EventService());
            context.setAttribute("registrationService", new EventRegistrationService());


            System.out.println("Application context initialized successfully");

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize application context", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DbConnection.destroy();
        System.out.println("Application context destroyed");
    }
}