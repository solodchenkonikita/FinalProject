package com.epam.service.impl;

import com.epam.dao.UserDao;
import com.epam.dao.impl.UserDaoImpl;
import com.epam.entity.User;
import com.epam.exception.DBException;
import com.epam.service.UserManager;

import java.sql.Date;
import java.util.List;

public class UserManagerImpl implements UserManager {

    UserDao userDao = new UserDaoImpl();

    @Override
    public void addUser(User user) throws DBException {
        userDao.addUser(user);
    }

    @Override
    public User getUserByEmail(String email) throws DBException {
        return userDao.findUserByEmail(email);
    }

    @Override
    public String getUserEmailByEmail(String email) throws DBException {
        return userDao.findUserEmailByEmail(email);
    }

    @Override
    public List<User> findAllMasters() throws DBException {
        return userDao.findAllMasters();
    }

    @Override
    public List<User> getUsersWithBookingByDate(Date date) {
        return userDao.getUsersWithBookingByDate(date);
    }
}
