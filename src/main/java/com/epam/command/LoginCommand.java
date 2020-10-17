package com.epam.command;

import com.epam.Path;
import com.epam.entity.User;
import com.epam.exception.AppException;
import com.epam.service.UserManager;
import com.epam.service.impl.UserManagerImpl;
import com.epam.util.ActionType;
import com.epam.util.validation.FieldValidation;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand extends Command {
    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

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
     * Login user in system and open main page.
     *
     * @return path to the page.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AppException {
        String result;
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!FieldValidation.isEmail(email)) {
            LOG.error("Email is not valid");
            throw new AppException("Email is not valid");
        }

        if (!FieldValidation.isPassword(password)) {
            LOG.error("Password is not valid");
            throw new AppException("Password is not valid");
        }

        UserManager manager = new UserManagerImpl();
        User user = manager.getUserByEmail(email);
        LOG.trace("User found: " + user);

        if (user == null || !password.equals(user.getPassword())) {
            LOG.error("Cannot find user with such email/password");
            throw new AppException("Cannot find user with such email/password");
        } else {
            session.setAttribute("user", user);
            result = Path.WELCOME_PAGE;
        }
        return result;
    }
}
