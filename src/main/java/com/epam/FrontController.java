package com.epam;

import com.epam.command.Command;
import com.epam.command.CommandManager;
import com.epam.exception.AppException;
import com.epam.util.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller.
 *
 * @author Solodchenko Nikita
 *
 */

public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(FrontController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response, ActionType.GET);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response, ActionType.POST);
    }

    /**
     * Handles all requests coming from the client by executing the specified
     * command name in a request. Implements PRG pattern by checking action type
     * specified by the invoked method.
     *
     * @param request
     * @param response
     * @param actionType
     * @throws IOException
     * @throws ServletException
     * @see ActionType
     */
    private void process(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException {
        LOG.debug("Start processing in Controller");

        String commandName = request.getParameter("command");
        LOG.trace("Request parameter: 'command' = " + commandName);

        Command command = CommandManager.get(commandName);
        LOG.trace("Obtained 'command' = " + command);

        String path = null;
        try {
            path = command.execute(request, response, actionType);
        } catch (AppException ex) {
            request.setAttribute("errorMessage", ex.getMessage());
        }
        LOG.trace("Path address = " + path);

        if (path == null) {
            LOG.trace("Forward to error page");
            LOG.debug("Controller proccessing finished");
            request.getRequestDispatcher(Path.ERROR_PAGE).forward(request, response);
        } else {
            if (actionType == ActionType.GET) {
                LOG.trace("Forward to address = " + path);
                LOG.debug("Controller proccessing finished");
                RequestDispatcher disp = request.getRequestDispatcher(path);
                disp.forward(request, response);
            } else if (actionType == ActionType.POST) {
                LOG.trace("Redirect to address = " + path);
                LOG.debug("Controller proccessing finished");
                response.sendRedirect(path);
            }
        }
    }
}
