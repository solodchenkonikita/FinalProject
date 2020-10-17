package com.epam.command;

import com.epam.Path;
import com.epam.util.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand extends Command {

    private static final long serialVersionUID = 5413501849604794106L;

    private static final Logger LOG = Logger.getLogger(NoCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType) throws IOException, ServletException {
        LOG.debug("Command execution starts");

        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);
        LOG.error("Set the request attribute: 'errorMessage' = " + errorMessage);

        LOG.debug("Command execution finished");
        return Path.ERROR_PAGE;
    }
}
