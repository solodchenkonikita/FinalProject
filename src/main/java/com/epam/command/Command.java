package com.epam.command;

import com.epam.exception.AppException;
import com.epam.util.ActionType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable {
    private static final long serialVersionUID = 6038148240950618990L;

    public abstract String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException;

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
