package com.epam.service.impl;

import com.epam.dao.UserDao;
import com.epam.dao.impl.UserDaoImpl;
import com.epam.entity.User;
import com.epam.exception.DBException;
import com.epam.service.UserManager;
import com.epam.util.ConnectionPool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class UserManagerImpl implements UserManager {

    UserDao userDao = new UserDaoImpl();

    @Override
    public void addUser(User user) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            userDao.addUser(connection, user);
        } finally {
            close(connection);
        }
    }

    @Override
    public User getUserByEmail(String email) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return userDao.findUserByEmail(connection, email);
        } finally {
            close(connection);
        }
    }

    @Override
    public String getUserEmailByEmail(String email) throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return userDao.findUserEmailByEmail(connection, email);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<User> findAllMasters() throws DBException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return userDao.findAllMasters(connection);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<User> getUsersWithBookingByDate(Date date) {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            return userDao.getUsersWithBookingByDate(connection, date);
        } finally {
            close(connection);
        }
    }

    private void close(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
