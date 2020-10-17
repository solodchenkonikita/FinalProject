package com.epam.command;

import com.epam.Path;
import com.epam.entity.Timetable;
import com.epam.exception.AppException;
import com.epam.service.AdministratorTimetableManager;
import com.epam.service.TimetableManager;
import com.epam.service.impl.AdministratorTimetableManagerImpl;
import com.epam.service.impl.TimetableManagerImpl;
import com.epam.util.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdministratorCommand extends Command {

    private static final long serialVersionUID = -6556407791338961555L;

    private static final Logger LOG = Logger.getLogger(AdministratorCommand.class);

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

    private String doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AppException {
        LOG.debug("Start executing Command");
        String result = null;
        String page = request.getParameter("page");
        String strDate = request.getParameter("date");
        String query = request.getQueryString();
        query = "controller?" + query;

        int pageNumber = 1;
        if (page != null) {
            pageNumber = Integer.parseInt(page);
            query = query.substring(0, query.lastIndexOf('&'));
        }
        LOG.trace("Page = " + page);
        LOG.trace("Query = " + query);
        Date date;
        if (strDate == null) {
            date = Date.valueOf(LocalDate.now());
        } else {
            date = Date.valueOf(strDate);
        }
        LOG.trace("Date = " + date);

        AdministratorTimetableManager manager = new AdministratorTimetableManagerImpl();
        int countOfBooking = manager.getTimetableWithBookingByDateCount(date);
        int countOfPages = ((countOfBooking - 1) / 10) + 1;
        LOG.trace("Page count = " + countOfPages);
        LOG.trace("Count of booking = " + countOfBooking);
        int[] pages = new int[countOfPages];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i + 1;
        }
        if (pageNumber > countOfPages) {
            pageNumber = 1;
        }
        int start = (pageNumber - 1) * 10;
        int limit = 10;

        List<Timetable> list = manager.getTimetableWithBookingByDate(date, start, limit);
        LOG.trace("Timetables found: " + list);

        TimetableManager timetableManager = new TimetableManagerImpl();
        List<Date> dates = timetableManager.getDates(Date.valueOf(LocalDate.now()));
        LOG.trace("Dates found: " + dates);

        Map<Integer, List<Timetable>> mastersFreeTimetable = new LinkedHashMap<>();
        for (Timetable t : list) {
            int masterId = t.getMaster().getId();
            if (!mastersFreeTimetable.containsKey(masterId)) {
                mastersFreeTimetable.put(masterId, manager.getMasterFreeTimetableByDate(date, masterId));
            }
        }

        request.setAttribute("timetableList", list);
        request.setAttribute("dateList", dates);
        request.setAttribute("pageList", pages);
        request.setAttribute("query", query);
        request.setAttribute("mastersFreeTimetable", mastersFreeTimetable);
        result = Path.ADMINISTRATOR_PAGE;

        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AppException {
        LOG.debug("Start executing Command");
        String result;
        String booking = request.getParameter("bookingId");
        String timetable = request.getParameter("newTimetableId");
        String action = request.getParameter("action");

        if (booking == null || action == null) {
            LOG.error("Some parameter is empty");
            throw new AppException("Some parameter is empty");
        }
        int bookingId = Integer.parseInt(booking);
        int newTimetableId = 0;
        if (timetable != null) {
            newTimetableId = Integer.parseInt(timetable);

        }
        AdministratorTimetableManager manager = new AdministratorTimetableManagerImpl();
        boolean flag = false;

        switch (action) {
            case "change status":
                flag = manager.setBookingPaid(bookingId);
                break;
            case "delete":
                flag = manager.deleteBooking(bookingId);
                break;
            case "changeSlot":
                flag = manager.changeClientBookingTime(bookingId, newTimetableId);
                break;
            default:
                LOG.error("Action undefined");
                throw new AppException("Action undefined");
        }

        if (!flag) {
            LOG.error("Cannot execute action " + action);
            throw new AppException("Cannot execute action " + action);
        }
        result = Path.ADMINISTRATOR_PAGE_FORWARD;
        return result;
    }
}
