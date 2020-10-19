package com.epam.listener;

import com.epam.entity.User;
import com.epam.service.UserManager;
import com.epam.service.impl.UserManagerImpl;
import com.epam.util.MailUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Context listener.
 *
 * @author Solodchenko Nikita
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log("Servlet context initialization starts");

        ServletContext servletContext = sce.getServletContext();
        initLog4J(servletContext);
        initCommandManager();
        messageSender();

        log("Servlet context initialization finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log("Servlet context destruction starts");

        log("Servlet context destruction finished");
    }

    private void initLog4J(ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(servletContext
                    .getRealPath("WEB-INF/log4j.properties"));
        } catch (Exception ex) {
            LOG.error("Cannot configure Log4j", ex);
        }
        log("Log4J initialization finished");
        LOG.debug("Log4j has been initialized");
    }


    /**
     * Initializes CommandManager.
     */
    private void initCommandManager() {
        try {
            Class.forName("com.epam.command.CommandManager");
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException(
                    "Cannot initialize Command Manager", ex);
        }
    }

    private void messageSender() {
        Timer timer = new Timer("Timer");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("messageSender start");
                UserManager manager = new UserManagerImpl();
                java.sql.Date date = java.sql.Date.valueOf(LocalDate.now().minusDays(1));
                System.out.println("Sender date = " + date);
                List<User> users = manager.getUsersWithBookingByDate(date);
                System.out.println("List users -> " + users);
                for (User user : users) {
                    System.out.println("Check user: " + user);
                    MailUtils.sendEmailAboutComment(user);
                }
            }
        };

        Date firstTime = Date.from(LocalDate.now().plusDays(1).atTime(10, 0).toInstant(ZoneOffset.ofHours(3)));
        timer.schedule(timerTask, firstTime, TimeUnit.DAYS.toMillis(1));
    }

    /**
     * Logs actions to console
     *
     * @param msg to be logged
     */
    private void log(String msg) {
        System.out.println("[ContextListener] " + msg);
    }
}
