package com.epam.command;

import com.epam.Path;
import com.epam.entity.Timetable;
import com.epam.entity.User;
import com.epam.exception.AppException;
import com.epam.service.MasterTimetableManager;
import com.epam.service.TimetableManager;
import com.epam.service.impl.MasterTimetableManagerImpl;
import com.epam.service.impl.TimetableManagerImpl;
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

public class MasterCommand extends Command {

    private static final long serialVersionUID = -2349087714933394198L;

    private static final Logger LOG = Logger.getLogger(MasterCommand.class);

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
        String strDate = request.getParameter("date");
        HttpSession session = request.getSession();
        int masterId = ((User) session.getAttribute("user")).getId();

        Date date;
        if (strDate == null) {
            date = Date.valueOf(LocalDate.now());
        } else {
            date = Date.valueOf(strDate);
        }
        LOG.trace("Date: " + date);

        MasterTimetableManager manager = new MasterTimetableManagerImpl();

        List<Timetable> list = manager.getMasterTimetableByDate(date, masterId);
        LOG.trace("Timetables found: " + list);

        TimetableManager timetableManager = new TimetableManagerImpl();
        List<Date> dates = timetableManager.getDates(Date.valueOf(LocalDate.now()));
        LOG.trace("Dates found: " + dates);

        request.setAttribute("timetableList", list);
        request.setAttribute("dateList", dates);
        request.setAttribute("date", Date.valueOf(LocalDate.now()));
        result = Path.MASTER_PAGE;

        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AppException {
        LOG.debug("Start executing Command");
        String result = null;
        String status = request.getParameter("status");
        String booking = request.getParameter("bookingId");

        if (!"done".equals(status) || booking == null) {
            LOG.error("Wrong status or empty booking id");
            throw new AppException("Wrong status or empty booking id");
        }
        int bookingId = Integer.parseInt(booking);
        MasterTimetableManager manager = new MasterTimetableManagerImpl();
        boolean flag = manager.setBookingDone(bookingId);

        if (!flag) {
            LOG.error("Cannot change progress status");
            throw new AppException("Cannot change progress status");
        }
        result = Path.MASTER_PAGE_FORWARD;
        return result;
    }
}
