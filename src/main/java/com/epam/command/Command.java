package com.epam.command;

import com.epam.exception.AppException;
import com.epam.util.ActionType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Main interface for the Command pattern implementation.
 *
 * @author Solodcheko Nikita
 *
 */
public abstract class Command implements Serializable {
    private static final long serialVersionUID = 6038148240950618990L;

    /**
     * Execution method for command. Returns path to go to based on the user
     * request.
     *
     * @param request
     *            - user request
     * @param response
     *            - server response
     * @param actionType
     *            - user HTTP method
     * @return Address to go once the command is executed.
     * @throws IOException
     * @throws ServletException
     * @see ActionType
     */
    public abstract String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException;


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
