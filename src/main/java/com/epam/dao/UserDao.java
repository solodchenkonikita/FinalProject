package com.epam.dao;

import com.epam.entity.User;
import com.epam.exception.DBException;

import java.sql.Date;
import java.util.List;

/**
 * Work with user in database
 *
 * @author Nikita Solodchenko
 */
public interface UserDao {
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
    User findUserByEmail(String email) throws DBException;

    /**
     * Get user email by email
     *
     * @param email specified email.
     * @return user email which was found in database.
     */
    String findUserEmailByEmail(String email) throws DBException;

    /**
     * Get all masters
     *
     * @return list of user whose role is master.
     */
    List<User> findAllMasters() throws DBException;

    List<User> getUsersWithBookingByDate(Date date);

}
