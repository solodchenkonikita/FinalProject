package com.epam.command;

import com.epam.Path;
import com.epam.entity.Timetable;
import com.epam.entity.User;
import com.epam.exception.AppException;
import com.epam.service.ClientTimetableManager;
import com.epam.service.impl.ClientTimetableManagerImpl;
import com.epam.util.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Client command.
 *
 * @author Solodchenko Nikita
 *
 */
public class ClientCommand extends Command {

    private static final long serialVersionUID = -6383097338420978815L;

    private static final Logger LOG = Logger.getLogger(ClientCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException {
        LOG.debug("Start executing Command");
        String result = null;

        if (actionType == ActionType.GET) {
            result = doGet(request, response);
        } else if (ActionType.POST == actionType) {
            result = doPost(request, response);
        }

        LOG.debug("End executing command");
        return result;
    }

    /**
     * Forward to client page.
     *
     * @return path to the client page.
     */
    private String doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AppException {
        LOG.debug("Start executing Command");
        String result = null;
        HttpSession session = request.getSession();
        String page = request.getParameter("page");

        int userId = ((User) session.getAttribute("user")).getId();

        int pageNumber = 1;
        if (page != null) {
            System.out.println();
            pageNumber = Integer.parseInt(page);
        }
        LOG.trace("Page = " + pageNumber);
        ClientTimetableManager manager = new ClientTimetableManagerImpl();
        int countOfBooking = manager.getUserBookingCount(userId);
        int countOfPages = ((countOfBooking - 1) / 10) + 1;
        LOG.trace("Page count = " + countOfPages);
        LOG.trace("Booking count = " + countOfBooking);
        int pages[] = new int[countOfPages];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i + 1;
        }
        if (pageNumber > countOfPages) {
            pageNumber = 1;
        }
        int start = (pageNumber - 1) * 10;
        int limit = 10;
        List<Timetable> timetables = manager.getTimetableWithUserBooking(userId, start, limit);
        LOG.trace("Timetables found: " + timetables);

        Date date = Date.valueOf(LocalDate.now());

        request.setAttribute("timetables", timetables);
        request.setAttribute("date", date);
        request.setAttribute("pageList", pages);
        result = Path.CLIENT_PAGE;

        return result;
    }

    /**
     * Redirect user after grading master or adding comment.
     *
     * @return path to the client page.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AppException {
        LOG.debug("Start executing Command");
        String result = null;

        String bookingId = request.getParameter("bookingId");
        String masterId = request.getParameter("masterId");
        String markS = request.getParameter("mark");
        String comment = request.getParameter("comment");

        if (bookingId == null) {
            LOG.error("Wrong booking selected");
            throw new AppException("Wrong booking selected");
        }
        int booking = Integer.parseInt(bookingId);
        ClientTimetableManager manager = new ClientTimetableManagerImpl();
        boolean flag = false;
        if (masterId != null && markS != null) {
            int master = Integer.parseInt(masterId);
            int mark = Integer.parseInt(markS);
            flag = manager.gradeMaster(booking, mark, master);
        }
        if (comment != null) {
            flag = manager.setComment(booking, comment);
        }
        if (!flag) {
            LOG.error("Cannot grade master or set comment");
            throw new AppException("Cannot grade master or set comment");
        }
        result = Path.CLIENT_PAGE_FORWARD;
        return result;
    }
}
