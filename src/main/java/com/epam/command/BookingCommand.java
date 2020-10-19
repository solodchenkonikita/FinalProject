package com.epam.command;

import com.epam.Path;
import com.epam.entity.User;
import com.epam.exception.AppException;
import com.epam.service.TimetableManager;
import com.epam.service.impl.TimetableManagerImpl;
import com.epam.util.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Booking command.
 *
 * @author Solodchenko Nikita
 *
 */
public class BookingCommand extends Command {

    private static final long serialVersionUID = -2270234117555632821L;

    private static final Logger LOG = Logger.getLogger(BookingCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException {
        LOG.debug("Start executing Command");
        String result = null;

        if (actionType == ActionType.POST) {
            result = doPost(request, response);
        }

        LOG.debug("End executing command");
        return result;
    }

    /**
     * Redirect user after creating booking.
     *
     * @return path to the user page.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AppException {
        String result;
        String timetable = request.getParameter("timetableId");
        String service = request.getParameter("serviceId");

        if (service == null || timetable == null) {
            LOG.error("Chosen incorrect service or timetable");
            throw new AppException("Chosen incorrect service or master timeslot");
        }

        int timetableId = Integer.parseInt(timetable);
        LOG.trace("Timetable id found: " + timetableId);
        LOG.trace("Service id found: " + service);
        int serviceId = Integer.parseInt(service);
        LOG.trace("Service id found: " + serviceId);
        User client = (User) request.getSession().getAttribute("user");
        LOG.trace("Client found: " + client);

        TimetableManager manager = new TimetableManagerImpl();
        manager.createBooking(serviceId, client.getId(), timetableId);

        result = Path.CLIENT_PAGE_FORWARD;
        return result;
    }
}
