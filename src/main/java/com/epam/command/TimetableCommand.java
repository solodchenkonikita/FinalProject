package com.epam.command;

import com.epam.Path;
import com.epam.entity.Service;
import com.epam.entity.Timetable;
import com.epam.entity.User;
import com.epam.exception.AppException;
import com.epam.service.PriceListManager;
import com.epam.service.TimetableManager;
import com.epam.service.UserManager;
import com.epam.service.impl.PriceListManagerImpl;
import com.epam.service.impl.TimetableManagerImpl;
import com.epam.service.impl.UserManagerImpl;
import com.epam.util.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Timetable command.
 *
 * @author Solodchenko Nikita
 *
 */
public class TimetableCommand extends Command {

    private static final long serialVersionUID = -5958977384084208068L;

    private static final Logger LOG = Logger.getLogger(TimetableCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException {
        LOG.debug("Start executing Command");
        String result = null;

        if (actionType == ActionType.GET) {
            result = doGet(request, response);
        }

        LOG.debug("End executing command");
        return result;
    }

    /**
     * Forward to timetable page.
     *
     * @return path to the page with timetable.
     */
    private String doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AppException {
        String result;
        String strDate = request.getParameter("date");
        String sortOption = request.getParameter("sortOption");
        String filterByMaster = request.getParameter("filterByMaster");
        String filterByService = request.getParameter("filterByService");
        String page = request.getParameter("page");
        String query = request.getQueryString();

        query = "controller?" + query;

        int pageNumber = 1;
        if (page != null) {
            pageNumber = Integer.parseInt(page);
            query = query.substring(0, query.lastIndexOf('&'));
        }
        LOG.trace("Page: " + page);
        LOG.trace("Query: " + query);

        Time time = Time.valueOf("00:00:00");
        Date date;
        if (strDate == null) {
            date = Date.valueOf(LocalDate.now());
        } else {
            date = Date.valueOf(strDate);
        }
        LOG.trace("Date: " + date);
        if (date.equals(Date.valueOf(LocalDate.now()))) {
            time = new Time(System.currentTimeMillis());
        }
        LOG.trace("Time: " + time);
        int countOfTimetable;
        int countOfPages = 1;
        int[] pages = new int[0];

        TimetableManager manager = new TimetableManagerImpl();
        List<Timetable> list;

        if (sortOption != null && (sortOption.equals("masters") || sortOption.equals("marks"))) {
            countOfTimetable = manager.getTimetableByDateCount(date, time);
            countOfPages = ((countOfTimetable - 1) / 10) + 1;
            if (pageNumber > countOfPages) {
                pageNumber = 1;
            }
            int start = (pageNumber - 1) * 10;
            int limit = 10;
            if (sortOption.equals("masters")) {
                list = manager.getTimetableByDateAndSortByMasterName(date, time, start, limit);
            } else {
                list = manager.getTimetableByDateAndSortByMasterMark(date, time, start, limit);
            }

        } else if (filterByMaster != null) {
            int masterId = Integer.parseInt(filterByMaster);
            countOfTimetable = manager.getTimetableByDateAndMasterIdCount(date, time, masterId);
            countOfPages = ((countOfTimetable - 1) / 10) + 1;
            if (pageNumber > countOfPages) {
                pageNumber = 1;
            }
            int start = (pageNumber - 1) * 10;
            int limit = 10;
            list = manager.getTimetableByDateAndMasterId(date, masterId, time, start, limit);
        } else if (filterByService != null) {
            int serviceId = Integer.parseInt(filterByService);
            countOfTimetable = manager.getTimetableByDateAndServiceCount(date, time, serviceId);
            countOfPages = ((countOfTimetable - 1) / 10) + 1;
            if (pageNumber > countOfPages) {
                pageNumber = 1;
            }
            int start = (pageNumber - 1) * 10;
            int limit = 10;
            list = manager.getTimetableByDateAndService(date, serviceId, time, start, limit);
        } else {
            countOfTimetable = manager.getTimetableByDateCount(date, time);
            countOfPages = ((countOfTimetable - 1) / 10) + 1;
            if (pageNumber > countOfPages) {
                pageNumber = 1;
            }
            int start = (pageNumber - 1) * 10;
            int limit = 10;
            list = manager.getTimetable(date, time, start, limit);
        }
        LOG.trace("Timetables found: " + list);
        pages = new int[countOfPages];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i + 1;
        }
        LOG.trace("count of pages = " + countOfPages);
        PriceListManager serviceManager = new PriceListManagerImpl();
        List<Service> serviceList = serviceManager.getAllServices();
        LOG.trace("Services found: " + serviceList);

        List<Date> dates = manager.getDates(Date.valueOf(LocalDate.now()));
        LOG.trace("Dates found: " + dates);

        UserManager userManager = new UserManagerImpl();
        List<User> masters = userManager.findAllMasters();
        LOG.trace("Masters found: " + masters);

        Map<Integer, List<Service>> mastersServices = new LinkedHashMap<>();
        for (Timetable t : list) {
            int masterId = t.getMaster().getId();
            if (!mastersServices.containsKey(masterId)) {
                mastersServices.put(masterId, serviceManager.getServicesByMaster(masterId));
            }
        }

        request.setAttribute("timetableList", list);
        request.setAttribute("serviceList", serviceList);
        request.setAttribute("serviceList", serviceList);
        request.setAttribute("dateList", dates);
        request.setAttribute("mastersList", masters);
        request.setAttribute("servicesByMaster", mastersServices);
        request.setAttribute("pageList", pages);
        request.setAttribute("query", query);
        result = Path.TIMETABLE_PAGE;

        return result;
    }
}
