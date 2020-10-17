package com.epam.service;

import com.epam.entity.User;
import com.epam.exception.DBException;

import java.sql.Date;
import java.util.List;

public interface UserManager {
    /**
     * Add user to database.
     *
     * @param user specified user.
     */
    void addUser(User user) throws DBException;

    /**
     * Get user by email
     *
     * @param email specified email.
     * @return user who was found in database.
     */
    User getUserByEmail(String email) throws DBException;

    /**
     * Get user email by email
     *
     * @param email specified email.
     * @return user email which was found in database.
     */
    String getUserEmailByEmail(String email) throws DBException;

    List<User> findAllMasters() throws DBException;

    List<User> getUsersWithBookingByDate(Date date);

}
