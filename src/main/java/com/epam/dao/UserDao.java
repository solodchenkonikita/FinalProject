package com.epam.dao;

import com.epam.entity.User;
import com.epam.exception.DBException;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * User dao interface.
 *
 * @author Solodchenko Nikita
 *
 */
public interface UserDao {
    /**
     * Add user to database.
     *
     * @param user specified user.
     */
    void addUser(Connection connection, User user) throws DBException;

    /**
     * Get user by email
     *
     * @param connection connection to database.
     * @param email specified email.
     * @return user who was found in database.
     */
    User findUserByEmail(Connection connection, String email) throws DBException;

    /**
     * Get user email by email
     *
     * @param connection connection to database.
     * @param email specified email.
     * @return user email which was found in database.
     */
    String findUserEmailByEmail(Connection connection, String email) throws DBException;

    /**
     * Get all masters
     *
     * @param connection connection to database.
     * @return list of user whose role is master.
     */
    List<User> findAllMasters(Connection connection) throws DBException;

    /**
     * Get users with booking by date
     *
     * @param connection connection to database.
     * @param date specified date.
     * @return list of user whose role is master.
     */
    List<User> getUsersWithBookingByDate(Connection connection, Date date);

}
