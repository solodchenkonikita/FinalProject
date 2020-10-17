package com.epam.command;

import com.epam.Path;
import com.epam.entity.Service;
import com.epam.exception.AppException;
import com.epam.service.PriceListManager;
import com.epam.service.impl.PriceListManagerImpl;
import com.epam.util.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Invoked when user wants to see list of all prices.
 *
 * @author Nikita Solodchenko
 */
public class PriceListCommand extends Command {
    private static final long serialVersionUID = 6564661770722005397L;

    private static final Logger LOG = Logger.getLogger(PriceListCommand.class);


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
     * Forward user to page with list of all prices.
     *
     * @return path to the page.
     */
    private String doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AppException {
        String result = null;
        String page = request.getParameter("page");
        int pageNumber = 1;
        if (page != null) {
            pageNumber = Integer.parseInt(page);
        }

        PriceListManager manager = new PriceListManagerImpl();
        int countOfServices = manager.getCountOfService();
        int countOfPages = ((countOfServices - 1) / 10) + 1;

        int pages[] = new int[countOfPages];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i + 1;
        }

        if (pageNumber > countOfPages) {
            pageNumber = 1;
        }

        int start = (pageNumber - 1) * 10;
        int limit = 10;
        List<Service> services = manager.getChosenNumberOfServices(start, limit);
        LOG.trace("Services found: " + services);

        request.setAttribute("serviceList", services);
        request.setAttribute("pageList", pages);
        result = Path.PRICE_LIST_PAGE;

        return result;
    }

}
