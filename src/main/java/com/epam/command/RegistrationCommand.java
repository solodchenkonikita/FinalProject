package com.epam.command;

import com.epam.Path;
import com.epam.entity.Role;
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

/**
 * Registration command.
 *
 * @author Solodchenko Nikita
 *
 */
public class RegistrationCommand extends Command {

    private static final long serialVersionUID = 126923332637907843L;

    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

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
     * Redirect user after registration.
     *
     * @return path to the main page.
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Start executing Command");
        String path = null;
        System.out.println(request.getCharacterEncoding());
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("password_confirmation");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        if (password == null || !password.equals(passwordConfirmation)) {
            LOG.trace("Passwords do not match. Please try again.");
            throw new AppException("Passwords do not match. Please try again.");
        }

        boolean valid = FieldValidation.validateRegistrationParameters(email, password, firstName, lastName);

        if (!valid) {
            LOG.trace("Invalid input data for registration");
            throw new AppException("Invalid input data for registration");
        }

        UserManager manager = new UserManagerImpl();
        String userEmail = manager.getUserEmailByEmail(email);
        LOG.trace("User email found: " + userEmail);

        if (email.equals(userEmail)) {
            LOG.error("User with such email already exist");
            throw new AppException("User with such email already exist");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(Role.CLIENT.getName());
        manager.addUser(user);

        user.setPassword(null);
        HttpSession session = request.getSession();

        session.setAttribute("user", user);
        path = Path.WELCOME_PAGE;

        return path;

    }
}
