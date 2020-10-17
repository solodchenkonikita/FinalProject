package com.epam.command;

import com.epam.Path;
import com.epam.exception.AppException;
import com.epam.util.ActionType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LanguageCommand extends Command {

    private static final long serialVersionUID = 6043069943572736356L;

    private static final Logger LOG = Logger.getLogger(LanguageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException {
        LOG.debug("Start executing Command");
        String result = null;

        if (ActionType.POST == actionType) {
            result = doPost(request, response);
        }

        LOG.debug("End executing command");
        return result;
    }

    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AppException {
        LOG.debug("Start executing Command");
        final String action = "controller?";

        String url = request.getParameter("url");
        String lang = request.getParameter("language");
        LOG.trace("url: " + url + ", lang: " + lang);

        request.getSession().setAttribute("lang", lang);
        LOG.trace("Language has been changed to " + lang);

        if (url.equals("command=logout") || url.equals("")) {
            return Path.WELCOME_PAGE;
        }

        return action + url;

    }
}
